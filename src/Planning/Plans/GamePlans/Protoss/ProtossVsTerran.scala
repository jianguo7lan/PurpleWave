package Planning.Plans.GamePlans.Protoss

import Macro.BuildRequests._
import Planning.Composition.UnitMatchers.UnitMatchType
import Planning.Plans.Army.{ConsiderAttacking, ControlEnemyAirspace}
import Planning.Plans.Compound.{And, IfThenElse, Or, Parallel}
import Planning.Plans.Information.{ScoutAt, ScoutExpansionsAt}
import Planning.Plans.Macro.Automatic._
import Planning.Plans.Macro.BuildOrders.Build
import Planning.Plans.Macro.Milestones.{HaveUpgrade, UnitsAtLeast}
import Planning.Plans.Macro.Reaction.{EnemyBio, EnemyBioAllIn}
import ProxyBwapi.Races.Protoss

class ProtossVsTerran extends Parallel {
  
  description.set("Protoss vs Terran")
  
  private val lateGameBuild = Vector[BuildRequest] (
    RequestUnitAtLeast(1,   Protoss.CitadelOfAdun),
    RequestUnitAtLeast(8,   Protoss.Gateway),
    RequestUpgrade(         Protoss.ZealotSpeed,    1),
    RequestUnitAtLeast(10,  Protoss.Gateway),
    
    RequestUnitAtLeast(4,   Protoss.Nexus),
    RequestUnitAtLeast(2,   Protoss.Forge),
    RequestUnitAtLeast(1,   Protoss.Stargate),
    
    RequestUpgrade(         Protoss.GroundDamage,   1),
    RequestUpgrade(         Protoss.GroundArmor,    1),
    
    RequestUnitAtLeast(1,   Protoss.TemplarArchives),
    RequestUpgrade(         Protoss.GroundDamage,   2),
    RequestUpgrade(         Protoss.GroundArmor,    2),
    RequestUpgrade(         Protoss.GroundDamage,   3),
    RequestUpgrade(         Protoss.GroundArmor,    3)
  )
  
  private class RespondToBioAllInWithReavers extends IfThenElse(
    new EnemyBioAllIn,
    new Build(ProtossBuilds.TechReavers)
  )
  
  private class RespondToBioWithReavers extends IfThenElse(
    new EnemyBio,
    new Build(ProtossBuilds.TechReavers)
  )
  
  private class WhenSafeToTakeNatural extends IfThenElse(
    new Or(
      new UnitsAtLeast(3, UnitMatchType(Protoss.Dragoon)),
      new UnitsAtLeast(1, UnitMatchType(Protoss.Reaver))
    ),
    new BuildMiningBases(2)
  )
  
  private class WhenSafeToTakeThirdBase extends IfThenElse(
    new Or(
      new UnitsAtLeast(5, UnitMatchType(Protoss.Dragoon)),
      new UnitsAtLeast(1, UnitMatchType(Protoss.Reaver))
    ),
    new BuildMiningBases(3)
  )
  
  private class BuildDragoonsUntilWeHaveZealotSpeed extends IfThenElse(
    new And(
      new HaveUpgrade(Protoss.ZealotSpeed),
      new UnitsAtLeast(12, UnitMatchType(Protoss.Dragoon))),
    new TrainContinuously(Protoss.Zealot),
    new TrainContinuously(Protoss.Dragoon)
  )
  
  private class UpgradeReavers extends IfThenElse(
    new UnitsAtLeast(2, UnitMatchType(Protoss.Reaver)),
    new Build(RequestUpgrade(Protoss.ScarabDamage))
  )
  
  children.set(Vector(
    new BuildMiningBases(1),
    new Build(ProtossBuilds.OpeningTwoGate1015Dragoons),
    new RequireSufficientPylons,
    new TrainProbesContinuously,
    new BuildAssimilators,
    new RespondToBioAllInWithReavers,
    new WhenSafeToTakeNatural,
    new RespondToBioWithReavers,
    new WhenSafeToTakeThirdBase,
    new TrainContinuously(Protoss.Reaver, 2),
    new TrainContinuously(Protoss.Scout,  3),
    new BuildDragoonsUntilWeHaveZealotSpeed,
    new UpgradeReavers,
    new Build(lateGameBuild),
    new ScoutExpansionsAt(60),
    new ScoutAt(10),
    new ControlEnemyAirspace,
    new ConsiderAttacking
  ))
}
