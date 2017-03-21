package Micro

import Startup.With
import bwapi.TilePosition
import bwta.BWTA

import scala.collection.mutable

class Paths {
  
  //Cache ground distances with a LRU (Least-recently used) cache
  private val maxCacheSize = 100000
  private val impossiblyLargeDistance = Int.MaxValue / 1000
  private val distanceCache = new mutable.HashMap[(TilePosition, TilePosition), Int]
  private val distanceAge = new mutable.HashMap[(TilePosition, TilePosition), Int]
  
  def exists(origin:TilePosition, destination: TilePosition):Boolean = {
    groundPixels(origin, destination) < impossiblyLargeDistance
  }
  
  def groundPixels(origin:TilePosition, destination:TilePosition):Int = {
    val request = (origin, destination)
    if ( ! distanceCache.contains(request)) {
      cacheDistance(request)
    }
    distanceAge.put(request, With.frame)
    val result = distanceCache(request)
    limitCacheSize()
    
    if (result < 0) {
      return impossiblyLargeDistance
    }
    
    result
  }
  
  private def cacheDistance(request:(TilePosition, TilePosition)) {
    val distance = BWTA.getGroundDistance(request._1, request._2)
    distanceCache.put(request, distance.toInt)
  }
  
  private def limitCacheSize() {
    if (distanceCache.keys.size > maxCacheSize) {
      val cutoff = With.frame - 24 * 60
      distanceAge.filter(_._2 < cutoff).foreach(pair => {
        distanceCache.remove(pair._1)
        distanceAge.remove(pair._1)
      })
    }
  }
}
