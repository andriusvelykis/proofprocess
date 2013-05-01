package org.ai4fm.proofprocess.isabelle.core.patch

import scala.util.{Failure, Success, Try}

import org.eclipse.core.runtime.{CoreException, Platform}

import org.ai4fm.proofprocess.isabelle.core.IPatchActionHandler
import org.ai4fm.proofprocess.isabelle.core.IsabellePProcessCorePlugin.{error, log, plugin}
import org.ai4fm.proofprocess.isabelle.core.patch.IsabellePatches.PatchInfo

import isabelle.Isabelle_System


/**
 * Controls checks and calls to Isabelle patching process.
 * 
 * @author Andrius Velykis
 */
class IsabellePatcher {

  private var continueAfterPatch: Option[Boolean] = None

  /**
   * Checks and performs Isabelle patching for ProofProcess capture.
   * 
   * Returns false if PP capture should not be continued, true if everything is ok
   */
  def checkIsabellePatched(): Boolean = continueAfterPatch match {
    case Some(continue) => continue

    case None => {
      val continueCapture = Try(doCheckPatched()) match {

        case Failure(ex) => {
          log(error(Some(ex)))
          patchHandler foreach (_.reportPatchProblem(ex.getMessage))

          // do not continue PP capture
          false
        }

        case Success(continue) => continue
      }

      continueAfterPatch = Some(continueCapture)
      continueCapture
    }
  }


  private def doCheckPatched(): Boolean = {

    val isabelleHome = Isabelle_System.getenv_strict("ISABELLE_HOME")

    val unpatchedTest = IsabellePatches.findUnpatched(isabelleHome)

    unpatchedTest.fold(
      err => {
        patchHandler foreach (_.reportPatchProblem(err))
        false
      },
      patches => askAndPatch(isabelleHome, patches))
  }


  private def askAndPatch(isabelleHome: String, patches: List[PatchInfo]): Boolean =
    if (!patches.isEmpty) {
      // only ask the user if there is something to patch

      patchHandler match {
        case None => true// ignore

        case Some(handler) => {

          val patchedFiles = patches map (_.targetPath)

          if (handler.performPatch(patchedFiles)) {
            // user agreed - do the patch
            IsabellePatches.applyPatches(isabelleHome, patches)

            handler.reportPatchCompleted()
            // do not allow continuing
            false
          } else {
            // cancelled - allow capturing with whatever we have
            true
          }
        }
      }
    } else {
      // no need to patch - allow to continue
      true
    }


  private def PATCH_HANDLER_ID = plugin.pluginId + ".patchHandler"

  private def patchHandler: Option[IPatchActionHandler] = {
    val extensionRegistry = Platform.getExtensionRegistry
    val extensions = extensionRegistry.getConfigurationElementsFor(PATCH_HANDLER_ID)

    try {
      val execExts = extensions.toStream map (_.createExecutableExtension("class"))

      execExts.headOption match {
        case Some(h: IPatchActionHandler) => Some(h)

        case _ => {
          log(error(msg = Some("Cannot instantiate patch handler extension!")))
          None
        }
      }
    } catch {
      case ex: CoreException => {
        log(error(Some(ex)))
        None
      }
    }
  }

}

