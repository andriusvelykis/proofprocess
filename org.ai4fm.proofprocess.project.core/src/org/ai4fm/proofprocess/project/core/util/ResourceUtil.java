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
import org.eclipse.core.runtime.Path;

public class ResourceUtil {

	/**
	 * 
	 * @param filePath full path (e.g. {@link IResource#getLocation()})
	 * @return
	 */
	public static IProject findProject(IPath filePath) {
		
		Assert.isNotNull(filePath);
		
		List<IFile> files = findFile(URIUtil.toURI(filePath));
		
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
	
	public static List<IFile> findFile(URI uri) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = workspaceRoot.findFilesForLocationURI(uri);
		return Arrays.asList(files);
	}
	
	public static IProject findProject(URI uri) {
		
		Assert.isNotNull(uri);
		
		List<IFile> files = findFile(uri);
		
		// take the first one, if available
		if (!files.isEmpty()) {
			return files.get(0).getProject();
		}
		
		// try the Path part of the URI to use as IPath
		// TODO review
		String path = uri.getPath();
		if (path != null) {
			IPath resPath = Path.fromPortableString(path);
			return findProject(resPath);
		}
		
		// any other lookup options?
		// E.g. use the last-used project or similar?
		return null;
	}
	
	public static String getPath(IResource resource) {
		return resource.getLocation().toOSString();
	}
	
}
