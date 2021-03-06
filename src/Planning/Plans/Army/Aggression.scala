package Planning.Plans.Army

import Lifecycle.With
import Planning.Plans.Compound.Do

class Aggression(aggressionRatio: Double) extends Do(() => With.blackboard.aggressionRatio = aggressionRatio) {
  
  override def toString: String = "Aggression: " + aggressionRatio
}
