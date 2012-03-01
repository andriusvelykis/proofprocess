package org.ai4fm.proofprocess.project.core;

import java.io.IOException;

import org.ai4fm.proofprocess.project.Project;
import org.ai4fm.proofprocess.project.ProjectProofProcessFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;


public class ProofManager {
	
	public static final String PROOF_FOLDER = ".proofprocess/";
	private static final String PROOF_PROJECT_PATH = PROOF_FOLDER + "project.zevesproof";
	
	/**
	 * Key for the loaded project reference on resource.
	 */
	public final static QualifiedName PROP_PROOF_PROJECT = 
			new QualifiedName(ProjectProofProcessPlugin.PLUGIN_ID, "proofProject"); //$NON-NLS-1$
	
	public static Project getProofProject(IProject projectResource, IProgressMonitor monitor)
			throws CoreException {
		
		Project proofProject = (Project) projectResource.getSessionProperty(PROP_PROOF_PROJECT);
		if (proofProject == null) {
			
			// load project
			
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			} else {
				monitor = new SubProgressMonitor(monitor, 10);
			}
			
			try {
				
				Job.getJobManager().beginRule(projectResource, monitor);
				
				monitor.beginTask("Loading proof progress", IProgressMonitor.UNKNOWN);
				
				// check maybe it has already been loaded (double-checked locking)
				proofProject = (Project) projectResource.getSessionProperty(PROP_PROOF_PROJECT);
				if (proofProject == null) {
					proofProject = loadProject(getProofProjectFile(projectResource));
					projectResource.setSessionProperty(PROP_PROOF_PROJECT, proofProject);
				}
				
			} finally {
				Job.getJobManager().endRule(projectResource);
				monitor.done();
			}
		}
		
		return proofProject;
	}
	
	private static Project loadProject(IFile file) {
		
		initResourceFactories();
		
		// Create a ComposedAdapterFactory for all registered models
		AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
				adapterFactory, new BasicCommandStack());
		
		String path = file.getFullPath().toOSString();
		Resource emfResource = editingDomain.createResource(path);
		
		Project proofProject = null;
		
		if (file.exists()) {
			try {
				emfResource.load(null);
			} catch (IOException e) {
				ProjectProofProcessPlugin.log(e);
			}
			proofProject = (Project) emfResource.getContents().get(0);
		}
		
		if (proofProject == null) {
			// unable to load - init a new model file
			proofProject = ProjectProofProcessFactory.eINSTANCE.createProject();
			emfResource.getContents().add(proofProject);
		}
		
		return proofProject;
	}
	
	
	private static IFile getProofProjectFile(IProject projectResource) {
		return projectResource.getFile(PROOF_PROJECT_PATH);
	}
	
	private static void initResourceFactories() {
		
		// Register the XMI resource factory for the .zevesproof extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("zevesproof", new XMIResourceFactoryImpl());
	}

}
