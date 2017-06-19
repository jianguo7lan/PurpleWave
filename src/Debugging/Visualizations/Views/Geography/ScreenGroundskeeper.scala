package Debugging.Visualizations.Views.Geography

import Debugging.Visualizations.Rendering.DrawScreen
import Lifecycle.With

object ScreenGroundskeeper {
  
  def render() {
    
    With.game.drawTextScreen(185, 25,"Unplaced:")
    DrawScreen.column(
      5, 50,
      With.groundskeeper.sortByPriority(
        With.groundskeeper.unplaced)
        .map(_.toString))
    
    With.game.drawTextScreen(385, 25,"Placed:")
    DrawScreen.table(
      325, 50,
      With.groundskeeper.sortByPriority(
        With.groundskeeper.placed.keys)
        .map(key => Vector(With.groundskeeper.placed(key).tile.toString, key.toString)))
  }
}
