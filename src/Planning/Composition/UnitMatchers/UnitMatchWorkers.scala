package Planning.Composition.UnitMatchers

import ProxyBwapi.UnitInfo.UnitInfo

object UnitMatchWorkers extends UnitMatcher{
  override def accept(unit: UnitInfo): Boolean =
    unit.unitClass.isWorker
}
