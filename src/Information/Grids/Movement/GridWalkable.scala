package Information.Grids.Movement

import Information.Grids.AbstractGrid
import Lifecycle.With

class GridWalkable extends AbstractGrid[Boolean] {
  
  override def get(i: Int): Boolean = With.grids.walkableTerrain.get(i) && With.grids.walkableUnits.get(i)
  
  override def defaultValue: Boolean = false
}
