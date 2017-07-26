package Micro.Actions.Combat.Decisionmaking

import Mathematics.PurpleMath
import Micro.Actions.Action
import Planning.Yolo
import ProxyBwapi.UnitInfo.FriendlyUnitInfo

object FightOrFlight extends Action {
  
  override def allowed(unit: FriendlyUnitInfo): Boolean = {
    unit.action.canFight
  }
  
  override def perform(unit: FriendlyUnitInfo) {
    if (Yolo.active) {
      Engage.consider(unit)
    }
  
    val prioritizeTeam  = 2.0
    val prioritizeSelf  = 1.0
    val doomed          = unit.matchups.doomed
    val matchups        = unit.matchups.ifAt(24)
    
    unit.action.desireTeam        = unit.battle.map(_.desire).getOrElse(1.0)
    unit.action.desireIndividual  = PurpleMath.nanToInfinity(matchups.vpfDealingDiffused / matchups.vpfReceivingDiffused) // NaN: If we're taking no damage, great!
    unit.action.desireTotal       = PurpleMath.nanToInfinity(
      Math.pow(unit.action.desireTeam,        prioritizeTeam) *
      Math.pow(unit.action.desireIndividual,  prioritizeSelf)) // NaN: If the team has no preference and we're happy, great!
  
    if (doomed) {
      Engage.consider(unit)
    }
    if (unit.action.desireTotal < 1.0) {
      Disengage.consider(unit)
    }
    Engage.consider(unit)
  }
}
