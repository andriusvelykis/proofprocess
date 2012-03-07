package org.ai4fm.proofprocess.isabelle.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class IsabelleProofPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.ai4fm.proofprocess.isabelle.core"; //$NON-NLS-1$
	
	// The shared instance
	private static IsabelleProofPlugin plugin;

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static IsabelleProofPlugin getDefault() {
		return plugin;
	}

}
