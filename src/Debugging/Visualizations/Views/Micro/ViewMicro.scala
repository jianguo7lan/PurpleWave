package Debugging.Visualizations.Views.Micro

import Debugging.Visualizations.Views.View

object ViewMicro extends View {
  
  def render() {
    VisualizeHitPoints.render()
    VisualizeUnitsForeign.render()
    VisualizeUnitsOurs.render()
    VisualizeMovementHeuristics.render()
  }
}
