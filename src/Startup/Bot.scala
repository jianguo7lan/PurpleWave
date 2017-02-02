package Startup

import Development.{AutoCamera, Logger, Overlay}
import Processes.{Banker, Prioritizer, Recruiter}
import Types.Plans.Strategy.PlanWinTheGame
import bwapi.DefaultBWListener
import bwta.BWTA

class Bot() extends DefaultBWListener {

  override def onStart() {
    Logger.debug("Purple Wave, reporting in.")
    With.bank = new Banker
    With.recruiter = new Recruiter
    With.prioritizer = new Prioritizer
    With.gameplan = new PlanWinTheGame
    Logger.debug("Reading map")
    BWTA.readMap()
    Logger.debug("Analyzing map")
    BWTA.analyze()
    Logger.debug("Bot initialization complete.")
    
    val manualControl = true
    AutoCamera.enabled = ! manualControl
    
    if (manualControl) {
      With.game.setLocalSpeed(1)
      With.game.enableFlag(1) //Enable user input
    } else {
      With.game.setLocalSpeed(0)
    }
  }
  
  def _onFrame() {
    With.bank.recountResources()
    With.recruiter.recountUnits()
    With.prioritizer.reassignPriorities()
    With.gameplan.execute()
    Overlay.render()
    AutoCamera.render()
  }

  override def onFrame() {
    try {
      _onFrame()
    }
    catch {
      case exception:Exception =>
        exception.printStackTrace()
        Logger.debug(exception.getClass.getSimpleName)
        
        if (exception.getStackTrace.nonEmpty) {
          Logger.debug(
            exception.getStackTrace.head.getClassName
            + "."
            + exception.getStackTrace.head.getMethodName
            + "(): "
            + exception.getStackTrace.head.getLineNumber)
        }
    }
  }

  override def onUnitComplete(unit: bwapi.Unit) {
    AutoCamera.focusUnit(unit)
  }

  override def onUnitDestroy(unit: bwapi.Unit) {
    AutoCamera.focusUnit(unit)
  }

  override def onUnitDiscover(unit: bwapi.Unit) {
    AutoCamera.focusUnit(unit)
  }
}
