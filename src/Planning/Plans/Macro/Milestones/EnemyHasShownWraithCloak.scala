package Planning.Plans.Macro.Milestones

import Lifecycle.With
import Planning.Plan
import ProxyBwapi.Races.Terran

class EnemyHasShownWraithCloak extends Plan {
  
  description.set("Require enemy to have a tech")
  
  var triggered = false
  
  override def isComplete: Boolean = triggered
  
  override def onUpdate() {
    triggered = triggered || With.units.enemy.exists(u => u.cloaked && u.is(Terran.Wraith))
  }
}
