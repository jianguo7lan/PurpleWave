package Micro.Actions.Basic

import Lifecycle.With
import Micro.Actions.Action
import ProxyBwapi.UnitInfo.FriendlyUnitInfo

object Produce extends Action {
  
  override def allowed(unit: FriendlyUnitInfo): Boolean = {
    unit.trainingQueue.isEmpty
  }
  
  override def perform(unit: FriendlyUnitInfo) {
    
    if (unit.agent.toTrain.isDefined) {
      With.commander.build(unit, unit.agent.toTrain.get)
      unit.agent.intention.toTrain = None //Avoid building repeatedly
    }
    else if (unit.agent.toTech.isDefined) {
      With.commander.tech(unit, unit.agent.toTech.get)
    }
    else if (unit.agent.toUpgrade.isDefined) {
      With.commander.upgrade(unit, unit.agent.toUpgrade.get)
    }
  }
}
