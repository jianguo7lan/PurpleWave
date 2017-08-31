package Micro.Squads.Goals

import Mathematics.Points.Pixel
import Micro.Agency.Intention

class SquadDrop(pixel: Pixel) extends SquadGoal {
  
  override def toString: String = "Drop on " + pixel.zone.name
  
  def updateUnits() {
    val transports  = squad.recruits.filter   (_.isTransport)
    val passengers  = squad.recruits.filterNot(_.isTransport).filter( ! _.flying )
        
    squad.recruits.foreach(_.agent.intend(squad.client, new Intention {
      toTravel = Some(pixel)
    }))
    
    if (transports.nonEmpty) {
      passengers.foreach(passenger => {
        passenger.agent.lastIntent.toTravel = Some(transports.minBy(_.pixelDistanceFast(passenger)).pixelCenter)
        passenger.agent.lastIntent.canBerzerk = passenger.zone == pixel.zone
      })
    }
  }
  
  override def acceptsHelp              : Boolean = true
  override def shouldRequireAirToAir    : Boolean = false
  override def shouldRequireAirToGround : Boolean = true
  override def shouldRequireTransport   : Boolean = true
}
