package Micro.Targeting

import Startup.With
import Micro.Intentions.Intention
import ProxyBwapi.UnitInfo.UnitInfo

object Targets {
  def get(intent:Intention):Set[UnitInfo] = {
    With.units.inRadius(intent.unit.pixelCenter, intent.unit.range + 32 * 8)
      .filter(intent.unit.isEnemyOf)
      .filter(_.alive)
      .filter(_.visible)
      .filterNot(_.invincible)
      .filter(target => target.detected || ( ! target.cloaked && ! target.burrowed ))
      .filterNot(target =>   target.flying && ! intent.unit.attacksAir)
      .filterNot(target => ! target.flying && ! intent.unit.attacksGround)
  }
}
