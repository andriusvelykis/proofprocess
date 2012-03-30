package org.ai4fm.proofprocess.project.core;

import java.io.IOException;

import org.ai4fm.filehistory.FileHistoryProject;
import org.ai4fm.filehistory.FileVersion;
import org.ai4fm.filehistory.core.FileHistoryCorePlugin;
import org.ai4fm.filehistory.core.FileHistoryManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

public class ProofHistoryManager {

	/**
	 * Key for the loaded project reference on resource.
	 */
	public final static QualifiedName PROP_FILE_HISTORY = 
			new QualifiedName(ProjectProofProcessPlugin.PLUGIN_ID, "fileHistory"); //$NON-NLS-1$
	
	public static FileVersion syncFileVersion(IProject project, IPath filePath, String text,
			int syncPoint, IProgressMonitor monitor) throws CoreException {
		
		if (filePath.isAbsolute()) {
			// absolute to workspace - make relative to project
			filePath = filePath.makeRelativeTo(project.getLocation());
		}
		
		String filePathStr = filePath.toPortableString();
		return syncFileVersion(project, filePathStr, text, syncPoint, monitor);
	}
	
	public static FileVersion syncFileVersion(IProject projectResource, String path, String text, 
			int syncPoint, IProgressMonitor monitor) throws CoreException {
		
		FileHistoryManager historyManager = getHistoryManager(projectResource, monitor);
		
//		IPath p = new Path(path);
//		IPath relativePath = p.makeRelativeTo(projectResource.getLocation());
		
		FileVersion version = historyManager.syncFileVersion(
				projectResource.getLocation().toOSString(),
				path, text, syncPoint, monitor);

		// save the project history to include the new version
		FileHistoryProject historyProject = historyManager.getHistoryProject(monitor);
		if (historyProject.eResource().isModified()) {
			
			IFile versionFile = getVersionFile(historyManager, projectResource, version);
			// TODO avoid refreshing if nothing has been created
			versionFile.refreshLocal(IResource.DEPTH_ZERO, null);
			
			try {
				historyProject.eResource().save(ProofManager.SAVE_OPTIONS);
			} catch (IOException e) {
				throw new CoreException(FileHistoryCorePlugin.error(e));
			}			
		}
		
		return version;
	}

	private static IFile getVersionFile(FileHistoryManager historyManager,
			IProject projectResource, FileVersion version) {
		
		IPath versionPath = Path.fromOSString(historyManager.getAbsolutePath(version));
		IPath relativePath = versionPath.makeRelativeTo(projectResource.getLocation());
		
		IFile versionFile = projectResource.getFile(relativePath);
		return versionFile;
	}
	
	public static IFile getVersionFile(IProject projectResource, FileVersion version,
			IProgressMonitor monitor) throws CoreException {
		FileHistoryManager historyManager = getHistoryManager(projectResource, monitor);

		return getVersionFile(historyManager, projectResource, version);
	}
	
	public static FileHistoryManager getHistoryManager(IProject projectResource, IProgressMonitor monitor)
			throws CoreException {
		
		FileHistoryManager historyManager = 
				(FileHistoryManager) projectResource.getSessionProperty(PROP_FILE_HISTORY);
		if (historyManager == null) {
			
			// create manager
			
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			} else {
				monitor = new SubProgressMonitor(monitor, 10);
			}
			
			try {
				
				Job.getJobManager().beginRule(projectResource, monitor);
				
				monitor.beginTask("Initialising manager", IProgressMonitor.UNKNOWN);
				
				// check maybe it has already been loaded (double-checked locking)
				historyManager = (FileHistoryManager) projectResource.getSessionProperty(PROP_FILE_HISTORY);
				if (historyManager == null) {
					// TODO what if the project gets moved?
					String historyPath = projectResource.getLocation().append(ProofManager.PROOF_FOLDER).toOSString();
					historyManager = new FileHistoryManager(historyPath);
					projectResource.setSessionProperty(PROP_FILE_HISTORY, historyManager);
				}
				
			} finally {
				Job.getJobManager().endRule(projectResource);
				monitor.done();
			}
		}
		
		return historyManager;
	}
	
}
