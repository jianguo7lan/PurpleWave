package Planning.Plans.Zerg.GamePlans

import Lifecycle.With
import Macro.BuildRequests.RequestAtLeast
import Planning.Composition.UnitCounters.UnitCountOne
import Planning.Composition.UnitMatchers.UnitMatchWorkers
import Planning.Plans.Army.{Aggression, AllIn, Attack}
import Planning.Plans.Compound.{If, _}
import Planning.Plans.Macro.Automatic.{AddSupplyWhenSupplyBlocked, Gather}
import Planning.Plans.Macro.BuildOrders.{Build, FollowBuildOrder}
import Planning.Plans.Macro.Milestones.UnitsAtLeast
import Planning.Plans.Scouting.FoundEnemyBase
import ProxyBwapi.Races.Zerg

class Zerg4Pool extends Parallel {
  
  children.set(Vector(
    new Aggression(2.0),
    new If(
      new FoundEnemyBase,
      new Build(RequestAtLeast(3, Zerg.Drone)),
      new Build(RequestAtLeast(5, Zerg.Drone))),
    new Build(RequestAtLeast(1, Zerg.SpawningPool)),
  
    new If(
      new And(
        new UnitsAtLeast(5, Zerg.Drone),
        new UnitsAtLeast(1, Zerg.SpawningPool)),
      new Attack {
        attackers.get.unitCounter.set(UnitCountOne)
        attackers.get.unitMatcher.set(UnitMatchWorkers)
      }),
    new Build(RequestAtLeast(12,  Zerg.Zergling)),
    new AllIn(new UnitsAtLeast(12, Zerg.Zergling)),
    new If(
      new Check(() => With.units.ours.count(_.is(Zerg.Larva)) >= 2),
      new AddSupplyWhenSupplyBlocked),
    new Attack,
    new FollowBuildOrder,
    new Gather
  ))
}
