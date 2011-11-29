package org.ai4fm.filehistory.core;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.ai4fm.filehistory.FileEntry;
import org.ai4fm.filehistory.FileHistoryFactory;
import org.ai4fm.filehistory.FileHistoryProject;
import org.ai4fm.filehistory.FileVersion;
import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

public class FileHistoryManager {

	private static final String FILE_HISTORY_EXT = "filehistory";
	
	private static final String ENCODING = "UTF-8";
	
	/**
	 * Scheduling rule used for loading project resource.
	 */
	private static final ISchedulingRule SYNC_RULE = new ISchedulingRule() {
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}
	};
	
	private FileHistoryProject historyProject;
	
	private final String historyProjectPath;
	private final String versionsFolderPath;
	
	public FileHistoryManager(String historyFolderPath) {
		this(historyFolderPath, null);
	}
	
	public FileHistoryManager(String historyFolderPath, String explicitProjectName) {
		super();
		
		String projectFileName = explicitProjectName != null ? explicitProjectName : "project";
		historyProjectPath = new File(historyFolderPath, projectFileName + "." + FILE_HISTORY_EXT).getPath();
		
		String versionsFolderName = (explicitProjectName != null ? explicitProjectName + "-" : "") + "files";
		versionsFolderPath = new File(historyFolderPath, versionsFolderName).getPath();
	}
	
	public String getAbsolutePath(FileVersion version) {
		return new File(new File(historyProjectPath).getParent(), version.getPath()).getPath();
	}
	
	public FileVersion syncFileVersion(String basePath, String path, String text, int syncPoint, 
			IProgressMonitor monitor)
			throws CoreException {
		
		// retrieve file history records
		FileHistoryProject historyProject = getHistoryProject(monitor);
		
		// get file record for the given path
		FileEntry file = getHistoryFileEntry(historyProject, path);
		
//		long start = System.currentTimeMillis();
		
		if (text == null) {
			// load text from file
			File sourceFile = getFile(basePath, path);
			text = loadFile(sourceFile);
		}
		
		// calculate checksum for new content
		String newChecksum = calculateChecksum(text);
		
		// get the latest version
		List<FileVersion> versions = file.getVersions();
		if (!versions.isEmpty()) {
			FileVersion lastVersion = versions.get(versions.size() - 1);
			
			// check if new version contents have changed
			String lastChecksum = lastVersion.getChecksum();

			if (newChecksum.equals(lastChecksum)) {
				// same file contents - use last version
				return lastVersion;
			} else {
				
				if (syncPoint < 0) {
					syncPoint = text.length();
				}
				
				Assert.isLegal(syncPoint <= text.length());
				
				// different file contents - need to check the sync points
				int lastSyncPoint = lastVersion.getSyncPoint();
				String lastSyncChecksum = lastVersion.getSyncChecksum();
				
				boolean lastSyncMatches = checkLastSyncMatches(
						text, newChecksum, lastSyncPoint, lastSyncChecksum);
				
				if (lastSyncMatches) {
					if (lastSyncPoint >= syncPoint) {
						/*
						 * The current sync point is before the last one, but
						 * everything up to the last one still matches, so keep the
						 * last version (the change happened after the last sync)
						 */
						return lastVersion;
					} else {
						/*
						 * Everything up to the last sync matches, so update the
						 * version to the new one (since it will encompass the
						 * old sync and add the new one)
						 */
						String relativePath = lastVersion.getPath();
						File versionFile = new File(new File(historyProjectPath).getParentFile().toURI().resolve(relativePath));
						
						saveFileVersion(lastVersion, versionFile, text, newChecksum, syncPoint);
						return lastVersion;
					}
				}
			}
		}
		
		// need a new version here
		File targetFile = allocateNewFile();
		FileVersion version = FileHistoryFactory.eINSTANCE.createFileVersion();
		
		saveFileVersion(version, targetFile, text, newChecksum, syncPoint);
		
		file.getVersions().add(version);
		updated();
		
		return version;
	}

	private boolean checkLastSyncMatches(String text, String newChecksum, int lastSyncPoint,
			String lastSyncChecksum) throws CoreException {
		
		if (lastSyncPoint <= text.length()) {
			// check whether everything up to the last sync point matches
			String newChecksumAtLastSync = calculateChecksum(text, lastSyncPoint, newChecksum);
			
			if (newChecksumAtLastSync.equals(lastSyncChecksum)) {
				// everything up to the last sync point matches
				return true;
			}
		}
		
		return false;
	}
	
	private FileEntry getHistoryFileEntry(FileHistoryProject historyProject, String path) {
		for (FileEntry file : historyProject.getFiles()) {
			if (path.equals(file.getPath())) {
				return file;
			}
		}
		
		// no file - create new
		// TODO synchronize for multiple access?
		FileEntry file = FileHistoryFactory.eINSTANCE.createFileEntry();
		file.setPath(path);
		
		historyProject.getFiles().add(file);
		updated();
		
		return file;
	}
	
	private void updated() {
		historyProject.eResource().setModified(true);
	}
	
//	private FileVersion createFileCopyVersion(String copyPath, IProgressMonitor monitor)
//			throws CoreException {
//
//		File targetFile = allocateNewFile();
//		File sourceFile = getFile(copyPath);
//
//		File parent = targetFile.getParentFile();
//		if (!parent.exists() && !parent.mkdirs()) {
//			throw new CoreException(FileHistoryCorePlugin.error(
//					"Failed creating folder at path: " + parent.getPath(), null));
//		}
//
//		FileUtils.copyFile(sourceFile, targetFile);
//
//		return createFileVersion(targetFile);
//	}
	
	private File getFile(String basePath, String path) throws CoreException {
		File sourceFile = new File(basePath, path);
		if (!sourceFile.exists()) {
			throw new CoreException(FileHistoryCorePlugin.error(
					"Cannot locate file to copy at path: " + path, null));
		}
		
		return sourceFile;
	}
	
	private File allocateNewFile() {
		File file = null;
		do {
			// generate new file ID that does not exist
			String newFileId = UUID.randomUUID().toString();
			file = new File(versionsFolderPath, newFileId);
		} while (file.exists());
		
		return file;
	}
	
	private FileVersion saveFileVersion(FileVersion version, File targetFile, String text,
			String checksum, int syncPoint) throws CoreException {
		
		saveFile(targetFile, text);
		
		String relativePath = new File(historyProjectPath).getParentFile().toURI().relativize(targetFile.toURI()).getPath();
		
		version.setPath(relativePath);
		version.setTimestamp(System.currentTimeMillis());
		
		version.setChecksum(checksum);
		version.setSyncPoint(syncPoint);
		version.setSyncChecksum(calculateChecksum(text, syncPoint, checksum));
		
		return version;
	}
	
	private void saveFile(File targetFile, String text) throws CoreException {
		try {
			FileUtils.writeStringToFile(targetFile, text, ENCODING);
		} catch (IOException e) {
			throw new CoreException(FileHistoryCorePlugin.error(e));
		}
	}
	
	private String loadFile(File file) throws CoreException {
		try {
			return FileUtils.readFileToString(file, ENCODING);
		} catch (IOException e) {
			throw new CoreException(FileHistoryCorePlugin.error(e));
		}
	}

	private String calculateChecksum(String text, int textPoint, String fullChecksum) throws CoreException {
		
		if (textPoint == text.length()) {
			return fullChecksum;
		}
		
		return calculateChecksum(text, textPoint);
	}
	
	private String calculateChecksum(String text, int textPoint) throws CoreException {
		
		Assert.isLegal(textPoint <= text.length());
		
		return calculateChecksum(text.substring(0, textPoint));
	}	
	
	private String calculateChecksum(String text) throws CoreException {
		try {
			return hash(text, "SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new CoreException(FileHistoryCorePlugin.error(e));
		}
	}
	
	public static String hash(String text, String algorithm)
	        throws NoSuchAlgorithmException {
	    byte[] hash = MessageDigest.getInstance(algorithm).digest(text.getBytes());
	    BigInteger bi = new BigInteger(1, hash);
	    String result = bi.toString(16);
	    if (result.length() % 2 != 0) {
	        return "0" + result;
	    }
	    return result;
	}
	
	public FileHistoryProject getHistoryProject(IProgressMonitor monitor)
			throws CoreException {
		
		if (historyProject == null) {
			
			// load project
			
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			} else {
				monitor = new SubProgressMonitor(monitor, 10);
			}
			
			try {
				
				Job.getJobManager().beginRule(SYNC_RULE, monitor);
				
				monitor.beginTask("Loading file history", IProgressMonitor.UNKNOWN);
				
				// check maybe it has already been loaded (double-checked locking)
				if (historyProject == null) {
					historyProject = loadProject(historyProjectPath);
				}
				
			} finally {
				Job.getJobManager().endRule(SYNC_RULE);
				monitor.done();
			}
		}
		
		return historyProject;
	}
	
	private static FileHistoryProject loadProject(String projectFilePath) {
		
		initResourceFactories();
		
		// Create a ComposedAdapterFactory for all registered models
		AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
				adapterFactory, new BasicCommandStack());
		
//		Resource emfResource = editingDomain.createResource(projectFilePath);
		
	    URI uri = URI.createFileURI(projectFilePath);
	    Resource emfResource = editingDomain.getResourceSet().createResource(uri);
		
		File file = new File(projectFilePath);
		FileHistoryProject historyProject = null;
		
		if (file.exists()) {
			try {
				emfResource.load(null);
			} catch (IOException e) {
				FileHistoryCorePlugin.log(e);
			}
			historyProject = (FileHistoryProject) emfResource.getContents().get(0);
		}
		
		if (historyProject == null) {
			// unable to load - init a new model file
			historyProject = FileHistoryFactory.eINSTANCE.createFileHistoryProject();
			emfResource.getContents().add(historyProject);
		}
		
		return historyProject;
	}
	
	private static void initResourceFactories() {
		
		// Register the XMI resource factory for the .filehistory extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put(FILE_HISTORY_EXT, new XMIResourceFactoryImpl());
	}
	
}
