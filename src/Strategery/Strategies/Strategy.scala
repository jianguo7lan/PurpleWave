package Strategery.Strategies

import Planning.Plan
import bwapi.Race

abstract class Strategy {
  
  override def toString: String = getClass.getSimpleName.replace("$", "")
  
  lazy val gameplan: Option[Plan] = None
  
  def options: Iterable[Strategy] = Iterable.empty
  
  def islandMaps        : Boolean         = false
  def groundMaps        : Boolean         = true
  def ourRaces          : Iterable[Race]  = Vector(Race.Terran, Race.Protoss, Race.Zerg, Race.Random)
  def enemyRaces        : Iterable[Race]  = Vector(Race.Terran, Race.Protoss, Race.Zerg, Race.Random)
  def startLocationsMin : Int = 2
  def startLocationsMax : Int = 24
}
