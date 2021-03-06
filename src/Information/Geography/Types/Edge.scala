package Information.Geography.Types

import Lifecycle.With
import Mathematics.Points.Pixel
import bwta.Chokepoint

import scala.collection.immutable.Seq

class Edge(choke: Chokepoint) {
  
  def otherSideof(zone: Zone): Zone =
    if (zones.head == zone)
      zones.last
    else
      zones.head // On Pathfinder there's an edge with the same zone on both sides. Grrr.
  
  lazy val centerPixel  : Pixel = new Pixel(choke.getCenter)
  lazy val radiusPixels : Double = choke.getWidth / 2
  lazy val sidePixels   : Seq[Pixel] = Vector(new Pixel(choke.getSides.first), new Pixel(choke.getSides.second))
  lazy val zones        : Vector[Zone] =
    Vector(
      choke.getRegions.first,
      choke.getRegions.second)
    .map(region => With.geography.zones.minBy(
      _.centroid.pixelCenter.pixelDistanceSquared(
        new Pixel(region.getCenter))))
  
  def contains(pixel: Pixel): Boolean = centerPixel.pixelDistanceFast(pixel) <= radiusPixels
}