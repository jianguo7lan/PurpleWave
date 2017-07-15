package Planning.Plans.Army

import Lifecycle.With
import Micro.Intent.Intention
import Planning.Composition.Property
import Planning.Composition.ResourceLocks.LockUnits
import Planning.Composition.UnitCounters.UnitCountOne
import Planning.Composition.UnitMatchers.UnitMatchDetectors
import Planning.Composition.UnitPreferences.UnitPreferClose
import Planning.Plan

class ClearBurrowedBlockers extends Plan {
  
  val detector = new Property(new LockUnits)
  detector.get.unitMatcher.set(UnitMatchDetectors)
  detector.get.unitCounter.set(UnitCountOne)
  
  override def onUpdate(): Unit = {
    
    val target = With.units.ours
      .find(u => u.actionState.toBuild.exists(_.isTownHall))
      .flatMap(_.actionState.toBuildTile.map(_.pixelCenter))
    
    if (target.isEmpty) return
    
    detector.get.unitPreference.set(UnitPreferClose(target.get))
    detector.get.acquire(this)
    
    detector.get.units.foreach(unit => With.executor.intend(new Intention(this, unit) {
      toTravel = target
      canCower = true
    }))
  }
}
