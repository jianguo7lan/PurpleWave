package Planning.Plans.Army

import Information.Geography.Types.{Base, Zone}
import Lifecycle.With
import Planning.Plan

import scala.collection.mutable

class ControlMap(val attack: Boolean = false) extends Plan {
  
  private val zones = new mutable.HashMap[Zone, ControlZone]
  
  protected override def onUpdate() {
    initialize()
    zones.keys.toList
      .filter(zoneValue(_) > 0.0)
      .sortBy(-zoneValue(_))
      .foreach(zones(_).update())
  }
  
  private def initialize() {
    if (zones.nonEmpty) return
    With.geography.zones.foreach(zone => zones.put(zone, new ControlZone(zone)))
  }
  
  private def zoneValue(zone: Zone): Double = {
    zone.bases.map(baseValue).sum
  }
  
  private def baseValue(base: Base): Double = {
    (5.0 + base.workers.size) *
      (
        if (base.owner.isFriendly)
          100.0
        else if (base.owner.isEnemy)
          (if (attack) 50.0 else 0.0)
        else if (base.planningToTake)
          20.0
        else
          1.0
      )
  }
}
