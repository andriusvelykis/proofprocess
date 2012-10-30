package org.ai4fm.filehistory.core;

import java.io.File;
import java.io.IOException;

import org.ai4fm.filehistory.FileHistoryProject;
import org.ai4fm.filehistory.core.internal.FileHistoryCorePlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import scala.Option;


/**
 * @author Andrius Velykis
 * @deprecated old-style serialization into XML file: now only a loader is left to migrate to CDO
 */
@Deprecated
public class XmlFileHistoryManager {

	private static final String FILE_HISTORY_EXT = "filehistory";
	
	private static <T> Option<T> none() { 
		return Option.apply(null);
	}
	
	private static Option<String> noneStr() { 
		return none();
	}
	
	public static FileHistoryProject getHistoryProject(String historyFolderPath,
			IProgressMonitor monitor) throws CoreException {

		String historyProjectPath = new File(historyFolderPath, "project." + FILE_HISTORY_EXT).getPath();
		
		// load project

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		} else {
			monitor = new SubProgressMonitor(monitor, 10);
		}

		try {
			monitor.beginTask("Loading file history", IProgressMonitor.UNKNOWN);

			return loadProject(historyProjectPath);

		} finally {
			monitor.done();
		}
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
		
		// if unable to load, return null
		return historyProject;
	}
	
	private static void initResourceFactories() {
		
		// Register the XMI resource factory for the .filehistory extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put(FILE_HISTORY_EXT, new XMIResourceFactoryImpl());
	}
	
}
