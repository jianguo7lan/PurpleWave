package Planning.Plans.Terran

import Macro.BuildRequests.{RequestAtLeast, RequestTech}
import Planning.Composition.UnitMatchers.UnitMatchWarriors
import Planning.Plans.Army.{ConsiderAttacking, DefendChokes, DefendHearts}
import Planning.Plans.Compound._
import Planning.Plans.Macro.Automatic.{Gather, RequireSufficientSupply, TrainContinuously, TrainWorkersContinuously}
import Planning.Plans.Macro.BuildOrders.{Build, FirstEightMinutes, FollowBuildOrder}
import Planning.Plans.Macro.Expanding.{BuildRefineries, RequireMiningBases}
import Planning.Plans.Macro.Milestones.UnitsAtLeast
import Planning.Plans.Scouting.ScoutAt
import ProxyBwapi.Races.Terran

class DestinyCloudFist extends Parallel {
  
  children.set(Vector(
    new RequireMiningBases(1),
    new FirstEightMinutes(
      new Build(
        RequestAtLeast(1, Terran.CommandCenter),
        RequestAtLeast(9, Terran.SCV),
        RequestAtLeast(1, Terran.SupplyDepot),
        RequestAtLeast(11, Terran.SCV),
        RequestAtLeast(1, Terran.Barracks),
        RequestAtLeast(13, Terran.SCV),
        RequestAtLeast(2, Terran.Barracks))),
    new RequireSufficientSupply,
    new TrainWorkersContinuously,
    new TrainContinuously(Terran.ScienceVessel, 2),
    new TrainContinuously(Terran.Marine),
    new RequireMiningBases(2),
    new BuildRefineries,
    new Build(RequestAtLeast(1, Terran.Factory)),
    new Build(RequestAtLeast(1, Terran.MachineShop)),
    new Build(RequestTech(Terran.SiegeMode)),
    new TrainContinuously(Terran.SiegeTankUnsieged),
    new Build(RequestAtLeast(1, Terran.EngineeringBay)),
    new Build(RequestAtLeast(2, Terran.MissileTurret)),
    new Build(RequestAtLeast(1, Terran.Starport)),
    new Build(RequestAtLeast(1, Terran.ScienceFacility)),
    new Build(RequestAtLeast(1, Terran.ControlTower)),
    new TrainContinuously(Terran.Wraith),
    new Build(RequestAtLeast(3, Terran.Factory)),
    new Build(RequestAtLeast(3, Terran.MachineShop)),
    new TrainContinuously(Terran.Barracks),
    new ScoutAt(14),
    new ConsiderAttacking,
    new If(
      new UnitsAtLeast(6, UnitMatchWarriors),
      new DefendChokes,
      new DefendHearts),
    new FollowBuildOrder,
    new Gather
  ))
}