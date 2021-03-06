package Planning.Plans.Macro.Milestones

import Planning.Plan
import Lifecycle.With
import Planning.Composition.UnitMatchers.{UnitMatchAnything, UnitMatcher}

class UnitsAtLeast(
  quantity  : Int         = 0,
  matcher   : UnitMatcher = UnitMatchAnything,
  complete  : Boolean     = false)
  
  extends Plan {
  
  description.set("Have at least " + quantity + " " + matcher)
  
  override def isComplete: Boolean = With.units.ours.count(unit =>
    ( ! complete || unit.complete) &&
    matcher.accept(unit)) >= quantity
}
