package org.ai4fm.proofprocess.project.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ai4fm.proofprocess.log.ProofLog;
import org.ai4fm.proofprocess.log.ProofProcessLogFactory;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;


public class ProofManager {
	
	public static final String PROOF_FOLDER = ".proofprocess/";
	private static final String PROOF_PROJECT_PATH = PROOF_FOLDER + "project.proof";
	private static final String PROOF_LOG_PATH = PROOF_FOLDER + "project.prooflog";
	
	public static final Map<String, Object> SAVE_OPTIONS;
	static {
		Map<String, Object> opts = new HashMap<String, Object>();
		// do not use XML 1.1, because its implementation in Java 1.6 has bugs
		// regarding large attributes due to old Xerces libraries.
//		opts.put(XMLResource.OPTION_XML_VERSION, "1.1");
		opts.put(XMLResource.OPTION_ENCODING, "UTF-8");
		SAVE_OPTIONS = Collections.unmodifiableMap(opts);
	}
	
	/**
	 * Key for the loaded project reference on resource.
	 */
	public final static QualifiedName PROP_PROOF_PROJECT = 
			new QualifiedName(ProjectProofProcessPlugin.PLUGIN_ID, "proofProject"); //$NON-NLS-1$
	
	/**
	 * Key for the loaded proof log reference on resource.
	 */
	public final static QualifiedName PROP_PROOF_LOG = 
			new QualifiedName(ProjectProofProcessPlugin.PLUGIN_ID, "proofLog"); //$NON-NLS-1$
	
	public static Project getProofProject(IProject projectResource, IProgressMonitor monitor)
			throws CoreException {
		
		return getProofModel(projectResource, PROP_PROOF_PROJECT, monitor);
	}
	
	public static ProofLog getProofLog(IProject projectResource, IProgressMonitor monitor)
			throws CoreException {
		
		return getProofModel(projectResource, PROP_PROOF_LOG, monitor);
	}
	
	private static <T extends EObject> T getProofModel(IProject projectResource,
			QualifiedName prop, IProgressMonitor monitor) throws CoreException {
		
		Object modelObj = projectResource.getSessionProperty(prop);
		if (modelObj == null) {
			loadProofModelsJob(projectResource, monitor);
			modelObj = projectResource.getSessionProperty(prop);
		}
		
		@SuppressWarnings("unchecked")
		T model = (T) modelObj;
		return model;
	}
	
	public static void loadProofModelsJob(IProject projectResource, IProgressMonitor monitor)
			throws CoreException {
		
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		} else {
			monitor = new SubProgressMonitor(monitor, 10);
		}
		
		try {
			
			Job.getJobManager().beginRule(projectResource, monitor);
			
			monitor.beginTask("Loading proof progress", IProgressMonitor.UNKNOWN);
			
			loadProofModels(projectResource);
			
		} finally {
			Job.getJobManager().endRule(projectResource);
			monitor.done();
		}
	}
	
	private static void loadProofModels(IProject projectRes) 
			throws CoreException {
		
		// check maybe they has already been loaded (double-checked locking)
		Project proj = (Project) projectRes.getSessionProperty(PROP_PROOF_PROJECT);
		ProofLog log = (ProofLog) projectRes.getSessionProperty(PROP_PROOF_LOG);
		
		if (proj != null && log != null) {
			// loaded
			return;
		}
		
		initResourceFactories();
		
		// Create a ComposedAdapterFactory for all registered models
		AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
				adapterFactory, new BasicCommandStack());
		
		Project proofProject = loadProofFile(editingDomain, getProofProjectFile(projectRes), 
				ProjectProofProcessFactory.eINSTANCE.createProject());
		
		ProofLog proofLog = loadProofFile(editingDomain, getProofLogFile(projectRes), 
				ProofProcessLogFactory.eINSTANCE.createProofLog());
		
		projectRes.setSessionProperty(PROP_PROOF_PROJECT, proofProject);
		projectRes.setSessionProperty(PROP_PROOF_LOG, proofLog);
	}
	
	private static <T extends EObject> T loadProofFile(AdapterFactoryEditingDomain editingDomain, IFile file, T empty) {
		
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		Resource emfResource = editingDomain.getResourceSet().createResource(uri);
		
		T root = null;
		
		if (file.exists()) {
			try {
				emfResource.load(null);
			} catch (IOException e) {
				ProjectProofProcessPlugin.log(e);
			}
			
			@SuppressWarnings("unchecked")
			T elem = (T) emfResource.getContents().get(0); 
			root = elem;
		}
		
		if (root == null) {
			// unable to load - init a new model file
			root = empty;
			emfResource.getContents().add(root);
		}
		
		return root;
	}
	
	private static IFile getProofProjectFile(IProject projectResource) {
		return projectResource.getFile(PROOF_PROJECT_PATH);
	}
	
	private static IFile getProofLogFile(IProject projectResource) {
		return projectResource.getFile(PROOF_LOG_PATH);
	}
	
	private static void initResourceFactories() {
		
		// Register the XMI resource factory for the .proof and .prooflog extensions
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("proof", new XMIResourceFactoryImpl());
		reg.getExtensionToFactoryMap().put("prooflog", new XMIResourceFactoryImpl());
	}

}
