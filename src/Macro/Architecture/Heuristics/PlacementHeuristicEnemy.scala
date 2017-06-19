package Macro.Architecture.Heuristics

import Lifecycle.With
import Macro.Architecture.BuildingDescriptor
import Mathematics.Points.Tile

object PlacementHeuristicEnemy extends PlacementHeuristic {
  
  override def evaluate(state: BuildingDescriptor, candidate: Tile): Double = {
  
    With.paths.groundPixels(candidate, With.intelligence.mostBaselikeEnemyTile)
    
  }
}
