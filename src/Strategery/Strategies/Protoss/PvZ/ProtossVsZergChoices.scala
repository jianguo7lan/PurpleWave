package Strategery.Strategies.Protoss.PvZ

import Strategery.Strategies.Strategy

object ProtossVsZergChoices {
  
  val openers: Vector[Strategy] = Vector(
    PvZEarlyFFEEconomic,
    PvZEarlyFFEConservative,
    PvZEarlyFFEGatewayFirst,
    PvZEarlyFFENexusFirst,
    PvZEarly2Gate)
  
  val midgames: Vector[Strategy] = Vector(
    PvZMidgame5GateDragoons,
    PvZMidgameCorsairDarkTemplar,
    PvZMidgameCorsairReaver,
    PvZMidgameCorsairSpeedlot,
    PvZMidgame2Stargate)
}
