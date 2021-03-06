package Information.Grids.ArrayTypes

import Lifecycle.With
import Mathematics.Points.Tile

abstract class AbstractGridTimestamp extends AbstractGridInt {
  
  def isSet(tile: Tile): Boolean = get(tile) >= frameUpdated
  
  var frameUpdated = 0
  val never: Int = -24 * 60 * 60
  
  override val defaultValue: Int = never
  reset()
  
  def everFlagged(tile: Tile): Boolean = get(tile) > 0
  def framesSince(tile: Tile): Int = frameUpdated - get(tile)
  
  final override def update() {
    frameUpdated = With.frame
    updateTimestamps()
  }
  
  protected def updateTimestamps()
}