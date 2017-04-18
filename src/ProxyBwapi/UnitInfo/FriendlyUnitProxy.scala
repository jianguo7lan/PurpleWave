package ProxyBwapi.UnitInfo
import Performance.Caching.{Cache, CacheFrame}
import ProxyBwapi.UnitClass.{UnitClass, UnitClasses}
import Lifecycle.With
import Mathematics.Pixels.{Pixel, Tile}
import ProxyBwapi.Players.{PlayerInfo, Players}
import bwapi._

import scala.collection.JavaConverters._

abstract class FriendlyUnitProxy(base:bwapi.Unit) extends UnitInfo(base) {
  
  override def equals(obj: Any): Boolean = obj.isInstanceOf[FriendlyUnitProxy] && obj.asInstanceOf[FriendlyUnitProxy].id == id
  override def hashCode(): Int = id.hashCode
  
  val cacheClass     = new Cache[UnitClass]     (5,  () =>  UnitClasses.get(base.getType))
  val cachePlayer    = new Cache[PlayerInfo]    (10, () =>  Players.get(base.getPlayer))
  val cachePixel     = new CacheFrame[Pixel]    (() =>  new Pixel(base.getPosition))
  val cacheTile      = new CacheFrame[Tile]     (() =>  new Tile(base.getTilePosition))
  val cacheCompleted = new CacheFrame[Boolean]  (() =>  base.isCompleted)
  val cacheExists    = new CacheFrame[Boolean]  (() =>  base.exists)
  val cacheSelected  = new CacheFrame[Boolean]  (() =>  base.isSelected)
  val cacheId        = new CacheFrame[Int]      (() =>  base.getID)
  val cachedFlying   = new CacheFrame[Boolean]  (() =>  base.isFlying)
  val cachedCloaked  = new CacheFrame[Boolean]  (() =>  base.isCloaked)
  val cachedStasised = new CacheFrame[Boolean]  (() =>  base.isStasised)
  
  ///////////////////
  // Tracking info //
  ///////////////////
  
  def player:PlayerInfo = cachePlayer.get
  def lastSeen:Int = With.frame
  def possiblyStillThere:Boolean = alive
  
  ////////////
  // Health //
  ////////////
  
  def alive                 : Boolean   = cacheExists.get
  def complete              : Boolean   = cacheCompleted.get
  def defensiveMatrixPoints : Int       = getDefenseMatrixPointsCache.get
  def hitPoints             : Int       = getHitPointsCache.get
  def initialResources      : Int       = getInitialResourcesCache.get
  def invincible            : Boolean   = isInvincibleCache.get
  def resourcesLeft         : Int       = getResourcesCache.get
  def shieldPoints          : Int       = getShieldsCache.get
  def unitClass             : UnitClass = cacheClass.get
  def plagued               : Boolean   = base.isPlagued
  
  private val getDefenseMatrixPointsCache   = new CacheFrame(() => base.getDefenseMatrixPoints)
  private val getHitPointsCache             = new CacheFrame(() => base.getHitPoints)
  private val getInitialResourcesCache      = new CacheFrame(() => base.getInitialResources)
  private val isInvincibleCache             = new CacheFrame(() => base.isInvincible)
  private val getResourcesCache             = new CacheFrame(() => base.getResources)
  private val getShieldsCache               = new CacheFrame(() => base.getShields)
  
  ////////////
  // Combat //
  ////////////
  
  def attacking                 : Boolean = base.isAttacking
  def attackStarting            : Boolean = base.isStartingAttack
  def attackAnimationHappening  : Boolean = base.isAttackFrame
  def airCooldownLeft           : Int     = airCooldownLeftCache.get
  def groundCooldownLeft        : Int     = groundCooldownLeftCache.get
  
  private val airCooldownLeftCache = new CacheFrame(() => base.getAirWeaponCooldown)
  private val groundCooldownLeftCache = new CacheFrame(() => base.getGroundWeaponCooldown)
  
  //////////////
  // Geometry //
  //////////////
  
  def pixelCenter : Pixel = cachePixel.get
  def tileTopLeft : Tile  = cacheTile.get
  def top         : Int   = base.getTop
  def left        : Int   = base.getLeft
  def right       : Int   = base.getRight
  def bottom      : Int   = base.getBottom
  
  ////////////
  // Orders //
  ////////////
  
  def gatheringMinerals : Boolean = base.isGatheringMinerals
  def gatheringGas      : Boolean = base.isGatheringGas
  
  private val badPositions = Vector(Position.Invalid, Position.None, Position.Unknown)
  def target            : Option[UnitInfo]  = targetCache.get
  def targetPixel       : Option[Pixel]     = { val position = base.getTargetPosition; if (badPositions.contains(position)) None else Some(new Pixel(position)) }
  def order             : Order             = base.getOrder
  def orderTarget       : Option[UnitInfo]  = { val target = base.getOrderTarget; if (target == null) None else With.units.getId(target.getID) }
  def orderTargetPixel  : Option[Pixel]     = { val position = base.getOrderTargetPosition; if (badPositions.contains(position)) None else Some(new Pixel(position)) }
  
  private val targetCache = new CacheFrame(() => { val target = base.getTarget; if (target == null) None else With.units.getId(target.getID) })
  
  /*
  def attacking:Boolean
  def attackFrame:Boolean
  def constructing:Boolean
  def following:Boolean
  def holdingPixel:Boolean
  def idle:Boolean
  def interruptible:Boolean
  def morphing:Boolean
  def repairing:Boolean
  def researching:Boolean
  def patrolling:Boolean
  def startingAttack:Boolean
  def training:Boolean
  def upgrading:Boolean
  */
  
  ///////////////////
  // Friendly only //
  ///////////////////
  
  def command:UnitCommand = base.getLastCommand
  def commandFrame:Int = base.getLastCommandFrame
  def trainingQueue: Iterable[UnitClass] = trainingQueueCache.get
  
  private val trainingQueueCache = new CacheFrame(() => base.getTrainingQueue.asScala.map(UnitClasses.get))
  
  ////////////////
  // Visibility //
  ////////////////
  
  def burrowed  : Boolean = isBurrowedCache.get
  def cloaked   : Boolean = cachedCloaked.get
  def detected  : Boolean = base.isDetected
  def visible   : Boolean = isVisibleCache.get
  
  private val isBurrowedCache = new CacheFrame(() => base.isBurrowed)
  private val isVisibleCache = new CacheFrame(() => base.isVisible)
  
  //////////////
  // Movement //
  //////////////
  
  def accelerating  : Boolean = base.isAccelerating
  def angle         : Double  = base.getAngle
  def braking       : Boolean = base.isBraking
  def ensnared      : Boolean = base.isEnsnared
  def flying        : Boolean = cachedFlying.get
  def lifted        : Boolean = base.isLifted
  def lockedDown    : Boolean = cachedIsLockedDown.get
  def maelstrommed  : Boolean = cachedIsMaelstrommed.get
  def sieged        : Boolean = base.isSieged
  def stasised      : Boolean = cachedStasised.get
  def stimmed       : Boolean = base.isStimmed
  def stuck         : Boolean = base.isStuck
  def velocityX     : Double  = base.getVelocityX
  def velocityY     : Double  = base.getVelocityY
  
  private val cachedIsLockedDown = new CacheFrame(() => base.isLockedDown)
  private val cachedIsMaelstrommed = new CacheFrame(() => base.isMaelstrommed)
  
  //////////////
  // Statuses //
  //////////////
  
  def beingConstructed    : Boolean = base.isBeingConstructed
  def beingGathered       : Boolean = base.isBeingGathered
  def beingHealed         : Boolean = base.isBeingHealed
  def blind               : Boolean = base.isBlind
  def carryingMinerals    : Boolean = carryingMineralsCache.get
  def carryingGas         : Boolean = carryingGasCache.get
  def powered             : Boolean = base.isPowered
  def selected            : Boolean = cacheSelected.get
  def targetable          : Boolean = base.isTargetable
  def underAttack         : Boolean = base.isUnderAttack
  def underDarkSwarm      : Boolean = base.isUnderDarkSwarm
  def underDisruptionWeb  : Boolean = base.isUnderDisruptionWeb
  def underStorm          : Boolean = base.isUnderStorm
  
  private val carryingMineralsCache = new CacheFrame(() => base.isCarryingMinerals)
  private val carryingGasCache = new CacheFrame(() => base.isCarryingGas)
}
