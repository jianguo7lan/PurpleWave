package Planning.Plans.Protoss.GamePlans

import Macro.BuildRequests.RequestAtLeast
import Planning.Plans.Army.{ConsiderAttacking, ControlMap}
import Planning.Plans.Compound.Parallel
import Planning.Plans.Information.Employ
import Planning.Plans.Macro.Automatic.{RequireSufficientPylons, TrainContinuously}
import Planning.Plans.Macro.BuildOrders.Build
import Planning.Plans.Macro.Expanding.RequireMiningBases
import Planning.Plans.Protoss.ProtossBuilds
import Planning.Plans.Protoss.Situational.TwoGatewaysAtNatural
import Planning.Plans.Scouting.ScoutAt
import ProxyBwapi.Races.Protoss
import Strategery.Strategies.Options.Protoss.PvR.{PvREarly2Gate99, PvREarly2Gate99AtNatural}

class ProtossVsRandom extends Parallel {
  
  description.set("Protoss vs Random")
  
  children.set(Vector(
    new RequireMiningBases(1),
    new Employ(PvREarly2Gate99AtNatural,
      new Parallel(
        new TwoGatewaysAtNatural,
        new Build(ProtossBuilds.OpeningTwoGate99_WithZealots: _*)
      )),
    new Employ(PvREarly2Gate99, new Build(ProtossBuilds.OpeningTwoGate99_WithZealots: _*)),
    new Employ(PvREarly2Gate99, new Build(ProtossBuilds.OpeningTwoGate1012: _*)),
    new RequireSufficientPylons,
    new TrainContinuously(Protoss.Zealot),
    new TrainContinuously(Protoss.Probe),
    new RequireMiningBases(2),
    new Build(
      RequestAtLeast(1, Protoss.Assimilator),
      RequestAtLeast(1, Protoss.CyberneticsCore),
      RequestAtLeast(3, Protoss.Gateway)),
    new ScoutAt(7),
    new ConsiderAttacking,
    new ControlMap
  ))
}