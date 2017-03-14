package Geometry.Grids.Real

import Geometry.Grids.Abstract.GridBoolean
import Startup.With

class GridBuildableTerrain extends GridBoolean {
  override def initialize() {
    positions.foreach(tilePosition => set(tilePosition, With.game.isBuildable(tilePosition)))
  }
}
