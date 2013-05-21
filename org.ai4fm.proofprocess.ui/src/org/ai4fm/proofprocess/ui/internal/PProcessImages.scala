package org.ai4fm.proofprocess.ui.internal

import java.net.{MalformedURLException, URL}

import org.eclipse.jface.resource.ImageDescriptor


/** ProofProcess image definitions.
  * 
  * When images are used in label providers (e.g. where Image) is required, they must be disposed manually.
  * For convenience, [[org.eclipse.jface.resource.ResourceManager]] could be used. 
  * 
  * @author Andrius Velykis
  */
object PProcessImages {

  private lazy val ICON_BASE_URL = PProcessUIPlugin.plugin.getBundle.getEntry("icons/")

  val MISSING_ICON = ImageDescriptor.getMissingImageDescriptor

  lazy val GOAL_IN = create("goal_in.gif")
  lazy val GOAL_OUT = create("goal_out.gif")
  lazy val SUCCESS = create("success.gif")

  private def create(iconPath: String) = {
    try {
      val url = new URL(ICON_BASE_URL, iconPath)
      ImageDescriptor.createFromURL(url)
    } catch {
      case _: MalformedURLException => MISSING_ICON
    }
  }

}
