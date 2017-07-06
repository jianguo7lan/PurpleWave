package Macro.Architecture

import Lifecycle.With
import Mathematics.Points.Tile
import Mathematics.Shapes.Spiral

object Surveyor {
  
  def candidates(blueprint: Blueprint): Iterable[Tile] = {
    
    // Ugh. Plasma completely confounds BWTA which wrecks everything related to zone logic.
    if (With.strategy.isPlasma) {
      return plasmaCandidates(blueprint: Blueprint)
    }
    
    if (blueprint.townHall) {
      With.geography.bases
        .filterNot(base => base.owner.isEnemy || base.zone.island)
        .map(_.townHallArea.startInclusive)
    }
    else if (blueprint.gas) {
      With.geography.bases
        .filter(_.townHall.exists(_.player.isUs))
        .flatMap(_.gas.map(_.tileTopLeft))
    }
    else {
        With.geography.ourBases
          .flatMap(_.zone.tiles)
          .toVector ++
        With.geography.zones
          .filter(zone =>
            ! zone.island
            && zone.owner.isNeutral
            && zone.edges.exists(_.zones.exists(_.owner.isUs)))
          .flatMap(_.tiles)
          .toVector
    }
  }
  
  def plasmaCandidates(blueprint: Blueprint): Iterable[Tile] = {
    if (blueprint.townHall) {
      With.geography.bases
        .toVector
        .sortBy(_.heart.tileDistanceFast(With.self.startTile))
        .take(3)
        .map(_.townHallArea.startInclusive)
    }
    else if (blueprint.gas) {
      With.geography.bases
        .filter(_.townHall.exists(_.player.isUs))
        .flatMap(_.gas.map(_.tileTopLeft))
    }
    else {
      Spiral
        .points(50)
        .map(With.self.startTile.add)
    }
  }
}
