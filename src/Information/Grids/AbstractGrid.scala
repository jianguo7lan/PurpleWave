package Information.Grids

import Lifecycle.With
import Mathematics.Points.Tile

abstract class AbstractGrid[T] {
  
  val width: Int = With.mapTileWidth
  val height: Int = With.mapTileHeight
  
  protected val length: Int = width * height
  
  def update() {}
  
  def valid(i: Int): Boolean          = i >= 0 && i < length
  def i(tileX: Int, tileY: Int): Int  = tileX + tileY * width
  def get(i: Int): T
  def get(tile: Tile): T              = get(tile.i)
  def defaultValue: T
  def repr(value: T): String          = value.toString
}
