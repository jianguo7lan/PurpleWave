package Micro.Actions.Combat.Attacking

import Lifecycle.With
import Micro.Actions.Action
import Planning.Yolo
import ProxyBwapi.UnitInfo.FriendlyUnitInfo

object Target extends Action {
  
  override def allowed(unit: FriendlyUnitInfo): Boolean = {
    unit.agent.canFight           &&
    unit.agent.toAttack.isEmpty   &&
    unit.canAttack                &&
    unit.matchups.targets.nonEmpty
  }
  
  override protected def perform(unit: FriendlyUnitInfo) {
    TargetUndetected.delegate(unit)
    TargetRelevant.delegate(unit)
    var canPillage = false
    canPillage ||= unit.agent.canPillage
    canPillage ||= unit.zone.owner.isEnemy
    canPillage ||= (With.intelligence.firstEnemyMain.isDefined && With.geography.enemyBases.isEmpty)
    canPillage ||= Yolo.active
    canPillage &&= unit.matchups.threatsInRange.isEmpty
    if (canPillage) {
      TargetAnything.delegate(unit)
    }
  }
}
