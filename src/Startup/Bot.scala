package Startup

import Development.{AutoCamera, Configuration, Logger, Overlay}
import Global.Allocation._
import Global.Information._
import Plans.GamePlans.PlanWinTheGame
import bwapi.DefaultBWListener
import bwta.BWTA

class Bot() extends DefaultBWListener {

  override def onStart() {
    try {
      With.logger = new Logger
      With.logger.debug("Loading BWTA.")
      BWTA.readMap()
      BWTA.analyze()
      With.logger.debug("BWTA analysis complete.")
      
      With.architect = new Architect
      With.bank = new Banker
      With.commander = new Commander
      With.economy = new Economy
      With.geography = new Geography
      With.gameplan = new PlanWinTheGame
      With.history = new History
      With.influence = new Influence
      With.memory = new Memory
      With.intelligence = new Intelligence
      With.prioritizer = new Prioritizer
      With.recruiter = new Recruiter
      With.scheduler = new Scheduler
  
      Overlay.enabled = Configuration.enableOverlay
      AutoCamera.enabled = Configuration.enableCamera
      With.game.enableFlag(1)
      With.game.setLocalSpeed(0)
    }
    catch { case exception:Exception => With.logger.onException(exception) }
  }

  override def onFrame() {
    try {
      With.onFrame()
      With.economy.onFrame()
      With.memory.onFrame()
      With.influence.onFrame()
      With.bank.onFrame()
      With.recruiter.onFrame()
      With.prioritizer.onFrame()
      With.gameplan.onFrame() //This needs to be last!
      With.scheduler.onFrame()
      With.commander.onFrame()
      Overlay.onFrame()
      AutoCamera.onFrame()
      _considerSurrender
    }
    catch { case exception:Exception => With.logger.onException(exception) }
  }

  override def onUnitComplete(unit: bwapi.Unit) {
    try {
      AutoCamera.focusUnit(unit)
    }
    catch { case exception:Exception => With.logger.onException(exception) }
  }

  override def onUnitDestroy(unit: bwapi.Unit) {
    try {
      With.memory.onUnitDestroy(unit)
      With.history.onUnitDestroy(unit)
      AutoCamera.focusUnit(unit)
    }
    catch { case exception:Exception => With.logger.onException(exception) }
  }

  override def onUnitDiscover(unit: bwapi.Unit) {
    try {
      AutoCamera.focusUnit(unit)
    }
    catch { case exception:Exception => With.logger.onException(exception) }
  }
  
  override def onEnd(isWinner: Boolean) {
    With.logger.debug(if (isWinner) "We won!" else "We lost!")
    With.logger.onEnd
    BWTA.cleanMemory()
  }
  
  def _try(action:() => Unit) = {
    try { action() }
    catch { case exception:Exception =>
      if (With.logger != null) {
        With.logger.onException(exception)
      } else {
        System.out.println(exception)
      }}
  }
  
  def _considerSurrender() = {
    if (With.game.self.supplyUsed == 0
      && With.game.self.minerals < 50
      && With.memory.knownEnemyUnits.exists(_.getType.isWorker)
      && With.memory.knownEnemyUnits.exists(_.getType.isResourceDepot)) {
      With.game.sendText("Good game!")
      With.game.leaveGame()
    }
  }
}
