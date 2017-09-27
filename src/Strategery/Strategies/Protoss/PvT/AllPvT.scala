package Strategery.Strategies.Protoss.PvT

import Strategery.Strategies.Strategy
import bwapi.Race

object AllPvT extends Strategy {
  
  override def choices: Iterable[Iterable[Strategy]] = Vector(
    Vector(
      //PvTEarly1GateStargate,
      PvTEarly14Nexus,
      PvTEarlyDTExpand,
      PvTEarly1GateRange,
      PvTEarly1015GateGoon))
  
  override def ourRaces    : Iterable[Race] = Vector(Race.Protoss)
  override def enemyRaces  : Iterable[Race] = Vector(Race.Terran)
}
