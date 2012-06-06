package org.ai4fm.proofprocess.ui.actions

import java.util.Collections
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.Command
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.commands.State
import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.IExecutableExtension
import org.eclipse.ui.PlatformUI
import org.eclipse.ui.commands.ICommandService
import org.eclipse.ui.commands.IElementUpdater
import org.eclipse.ui.menus.UIElement

/** A handler for [[org.eclipse.core.commands.State]]-based commands. Upon command execution, toggles
  * the state value (must be Boolean). Also reacts to state changes from other sources and updates
  * UI elements (e.g. toolbar buttons) accordingly.
  * 
  * To support correct UI element initialisation from command state, the command ID must be provided
  * as handler parameter with `#commandIdProperty` key. The State to get/set toggle state must be
  * defined under `#toggleStateKey` key.
  * 
  * @author Andrius Velykis
  */
class ToggleHandler extends AbstractHandler with IElementUpdater with IExecutableExtension {

  def commandIdProperty = "org.ai4fm.proofprocess.ui.toggleHandler.commandId"
    
  def toggleStateKey = "toggleState"
  
  // store command ID to query its state when updating UI elements
  var commandId: Option[String] = None

  
  @throws(classOf[ExecutionException])
  override def execute(event: ExecutionEvent): Object = {

    // upon execution, toggle the command state value and notify interested parties
    val cmdId = event.getCommand.getId
    this.commandId = Some(cmdId)

    // update toggled state
    commandState(event.getCommand) foreach { state =>

      boolVal(state) foreach { b =>
        // toggle the value
        state.setValue(!b)
      }

      // trigger element update
      commandService foreach { _.refreshElements(cmdId, Collections.emptyMap()) }
    }

    // return value is reserved for future APIs
    null
  }
  
  /** Retrieves platform command service */
  private def commandService: Option[ICommandService] =
    Option(PlatformUI.getWorkbench().getService(classOf[ICommandService]).asInstanceOf[ICommandService])

  /** Retrieves toggle state for the command */
  private def commandState(cmd: Command): Option[State] = Option(cmd.getState(toggleStateKey))

  /** Retrieves Boolean value from the state (or None if it is not Boolean) */
  private def boolVal(cmdState: State): Option[Boolean] = {
    val currentState: Any = cmdState.getValue
    currentState match {
      case b: Boolean => {
        Some(b)
      }
      case _ => None
    }
  }

  /** Parameterised handler initialisation */
  override def setInitializationData(configurationElement: IConfigurationElement, propertyName: String, data: Any) {

    data match {
      // extract the command ID from the initialisation data and store for later
      case CommandIdProperty(id) => this.commandId = Some(id)
      case _ => // ignore
    }
  }

  /** Extractor object to query the data map for the command ID property */
  private object CommandIdProperty {

    def unapply(data: Any): Option[String] =
      data match {
        // ensure it is a Map 
        case map: java.util.Map[_, _] => {
          // get the command ID property value, if available
          Option(map.get(commandIdProperty)) flatMap {
            _ match {
              case id: String => Some(id)
              case _ => None
            }
          }
        }
        case _ => None
      }
  }

  /** Updates the UI element according to current command toggle state */
  override def updateElement(element: UIElement, parameters: java.util.Map[_, _]): Unit =
    commandId foreach { cmdId =>
      commandService foreach { service =>
        commandState(service.getCommand(cmdId)) foreach { state =>
          boolVal(state) foreach { b =>
            element.setChecked(b)
          }
        }
      }
    }

}
