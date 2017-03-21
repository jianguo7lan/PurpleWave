package Information.Geography

import Geometry.Shapes.Circle
import Geometry.{Clustering, TileRectangle}
import ProxyBwapi.Races.Protoss
import ProxyBwapi.UnitInfo.ForeignUnitInfo
import Startup.With
import Utilities.TypeEnrichment.EnrichPosition._
import bwapi.TilePosition

class TownHallPositionCalculator {
  
  def calculate:List[TilePosition] = clusteredResourcePatches.flatMap(bestTownHallTile).toList
  
  private def clusteredResourcePatches:Iterable[Iterable[ForeignUnitInfo]] =
    Clustering.group[ForeignUnitInfo](
      resourcePatches,
      32 * 12,
      true,
      (unit) => unit.pixelCenter).values
  
  private def resourcePatches = With.units.neutral.filter(_.initialResources > 0)
  
  private def bestTownHallTile(resources:Iterable[ForeignUnitInfo]):Option[TilePosition] = {
    val centroid = resources.map(_.pixelCenter).centroid
    val centroidTile = centroid.toTilePosition
    val searchRadius = 10
    val candidates = Circle.points(searchRadius).map(centroidTile.add).filter(isLegalTownHallTile)
    if (candidates.isEmpty) return None
    Some(candidates.minBy(_.toPosition.add(64, 48).getDistance(centroid)))
  }
  
  private def isLegalTownHallTile(candidate:TilePosition):Boolean = {
  
    val buildingArea = Protoss.Nexus.tileArea.add(candidate)
    
    val exclusions =
      resourcePatches
        .map(resourcePatch => new TileRectangle(
          resourcePatch.tileTopLeft.subtract(3, 3),
          resourcePatch.tileTopLeft.add(3, 3).add(resourcePatch.unitClass.tileSize)))
    
    buildingArea.tiles.forall(With.grids.buildableTerrain.get) &&
      ! resourcePatches.view.map(resourcePatch => resourcePatch.tileArea.expand(3, 3)).exists(buildingArea.intersects)
  }
}
