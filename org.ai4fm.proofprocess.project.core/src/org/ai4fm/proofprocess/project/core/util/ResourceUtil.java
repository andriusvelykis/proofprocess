package org.ai4fm.proofprocess.project.core.util;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

public class ResourceUtil {

	/**
	 * 
	 * @param filePath full path (e.g. {@link IResource#getLocation()})
	 * @return
	 */
	public static IProject findProject(IPath filePath) {
		
		Assert.isNotNull(filePath);
		
		List<IFile> files = findFile(filePath);
		
		// take the first one, if available
		if (!files.isEmpty()) {
			return files.get(0).getProject();
		}
		
		// check projects - maybe some resource shares path with a project (e.g. if project is closed)
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			IPath projectPath = project.getLocation();
			
			if (projectPath.isPrefixOf(filePath)) {
				// use this project
				return project;
			}
		}
		
		// any other lookup options?
		// E.g. use the last-used project or similar?
		return null;
	}
	
	public static List<IFile> findFile(IPath filePath) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		URI path = URIUtil.toURI(filePath);
		IFile[] files = workspaceRoot.findFilesForLocationURI(path);
		return Arrays.asList(files);
	}
	
	public static String getPath(IResource resource) {
		return resource.getLocation().toOSString();
	}
	
}
