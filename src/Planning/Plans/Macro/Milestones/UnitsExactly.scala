package Planning.Plans.Macro.Milestones

import Lifecycle.With
import Planning.Composition.UnitMatchers.{UnitMatchAnything, UnitMatcher}
import Planning.Plan

class UnitsExactly(
  quantity:   Int         = 0,
  matcher:    UnitMatcher = UnitMatchAnything,
  complete:   Boolean     = false)
  
  extends Plan {
  
  description.set("Have exactly " + quantity + " " + matcher)
  
  override def isComplete: Boolean = With.units.ours.count(unit =>
    ( ! complete || unit.complete) &&
      matcher.accept(unit)) == quantity
}
