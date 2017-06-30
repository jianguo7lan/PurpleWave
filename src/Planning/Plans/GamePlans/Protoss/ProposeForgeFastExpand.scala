package Planning.Plans.GamePlans.Protoss

import Lifecycle.With
import Macro.Architecture.BuildingDescriptor
import Macro.Architecture.Heuristics.PlacementProfiles
import Planning.Plans.Compound.Parallel
import Planning.Plans.Macro.Build.ProposePlacement
import ProxyBwapi.Races.Protoss

class ProposeForgeFastExpand extends Parallel {
  
  override def onUpdate(): Unit = {
    children.set(proposals)
    super.onUpdate()
  }
  
  private lazy val proposals =
    Vector(
      new BuildingDescriptor(this, argPlacement = Some(PlacementProfiles.cannonPylon),    argBuilding = Some(Protoss.Pylon),         zone = With.geography.ourNatural.map(_.zone), argRangePixels = Some(4.0 * 32.0)),
      new BuildingDescriptor(this, argPlacement = Some(PlacementProfiles.groundDefense),  argBuilding = Some(Protoss.Forge),         zone = With.geography.ourNatural.map(_.zone), argRangePixels = Some(4.0 * 32.0)),
      new BuildingDescriptor(this, argPlacement = Some(PlacementProfiles.groundDefense),  argBuilding = Some(Protoss.PhotonCannon),  zone = With.geography.ourNatural.map(_.zone), argRangePixels = Some(7.0 * 32.0)),
      new BuildingDescriptor(this, argPlacement = Some(PlacementProfiles.groundDefense),  argBuilding = Some(Protoss.PhotonCannon),  zone = With.geography.ourNatural.map(_.zone), argRangePixels = Some(7.0 * 32.0)),
      new BuildingDescriptor(this, argPlacement = Some(PlacementProfiles.groundDefense),  argBuilding = Some(Protoss.PhotonCannon),  zone = With.geography.ourNatural.map(_.zone), argRangePixels = Some(7.0 * 32.0)),
      new BuildingDescriptor(this, argPlacement = Some(PlacementProfiles.groundDefense),  argBuilding = Some(Protoss.PhotonCannon),  zone = With.geography.ourNatural.map(_.zone), argRangePixels = Some(7.0 * 32.0)))
    .map(new ProposePlacement(_))
}
