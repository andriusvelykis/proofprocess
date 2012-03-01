package org.ai4fm.proofprocess.project.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class ProjectProofProcessPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.ai4fm.proofprocess.project.core"; //$NON-NLS-1$
	
	// The shared instance
	private static ProjectProofProcessPlugin plugin;

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
	public static ProjectProofProcessPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Writes the message to the plug-in's log
	 * 
	 * @param message
	 *            the text to write to the log
	 * @param exception
	 *            exception to capture in the log
	 */
	public static void log(String message, Throwable exception) {
		IStatus status = error(message, exception);
		getDefault().getLog().log(status);
	}
	
	/**
	 * Logs the exception to the plug-in's log. Exception's message is used as
	 * the log message.
	 * 
	 * @param exception
	 *            exception to capture in the log
	 */
	public static void log(Throwable exception) {
		log(exception.getMessage(), exception);
	}
	
	/**
	 * Returns a new error {@code IStatus} for this plug-in.
	 * 
	 * @param exception
	 *            exception to wrap in the error {@code IStatus}
	 * @return the error {@code IStatus} wrapping the exception
	 */
	public static IStatus error(Throwable exception) {
		return error(exception.getMessage(), exception);
	}
	
	/**
	 * Returns a new error {@code IStatus} for this plug-in.
	 * 
	 * @param message
	 *            text to have as status message
	 * @param exception
	 *            exception to wrap in the error {@code IStatus}
	 * @return the error {@code IStatus} wrapping the exception
	 */
	public static IStatus error(String message, Throwable exception) {
		if (message == null) {
			message = ""; 
		}
		return new Status(IStatus.ERROR, PLUGIN_ID, 0, message, exception);
	}

}