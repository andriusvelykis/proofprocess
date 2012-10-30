package org.ai4fm.filehistory.core;

import java.io.File;
import java.io.IOException;

import org.ai4fm.filehistory.FileHistoryFactory;
import org.ai4fm.filehistory.FileHistoryProject;
import org.ai4fm.filehistory.FileVersion;
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

import scala.Option;


public class FileHistoryManager {

	private static final String FILE_HISTORY_EXT = "filehistory";
	
	/**
	 * Scheduling rule used for loading project resource.
	 */
	private static final ISchedulingRule SYNC_RULE = new ISchedulingRule() {
		@Override
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}
	};
	
	private FileHistoryProject historyProject;
	
	private final String historyProjectPath;
	
	private final org.ai4fm.filehistory.core.internal.FileHistoryManager historyManager;
	
	public FileHistoryManager(String historyFolderPath) {
		this(historyFolderPath, null);
	}
	
	public FileHistoryManager(String historyFolderPath, String explicitProjectName) {
		super();
		
		String projectFileName = explicitProjectName != null ? explicitProjectName : "project";
		historyProjectPath = new File(historyFolderPath, projectFileName + "." + FILE_HISTORY_EXT).getPath();
		
		String versionsFolderName = (explicitProjectName != null ? explicitProjectName + "-" : "") + "files";
		
		this.historyManager = new org.ai4fm.filehistory.core.internal.FileHistoryManager(
				new File(historyFolderPath), new File(historyFolderPath, versionsFolderName));
	}
	
	public String getAbsolutePath(FileVersion version) {
		return new File(new File(historyProjectPath).getParent(), version.getPath()).getPath();
	}
	
	public FileVersion syncFileVersion(String basePath, String path, String text, int syncPoint, 
			IProgressMonitor monitor)
			throws CoreException {
		
		// retrieve file history records
		FileHistoryProject historyProject = getHistoryProject(monitor);
		
		// TODO syncPoint = -1
		FileVersion version = historyManager.syncFileVersion(historyProject, basePath, path, 
				Option.apply(text), Option.apply((Object) syncPoint), monitor);
		updated();
		
		return version;
	}

	private void updated() {
		historyProject.eResource().setModified(true);
	}
	
	private static <T> Option<T> none() { 
		return Option.apply(null);
	}
	
	private static Option<String> noneStr() { 
		return none();
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
				FileHistoryCorePlugin.log(FileHistoryCorePlugin.error(Option.<Throwable>apply(e), noneStr()));
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
