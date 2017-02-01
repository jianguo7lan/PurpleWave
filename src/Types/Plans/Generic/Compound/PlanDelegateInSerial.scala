package Types.Plans.Generic.Compound

import Types.Plans.Plan

class PlanDelegateInSerial extends Plan {
  
  override def isComplete():Boolean = {
    children.forall(_.isComplete)
  }
  
  override def execute() {
    var continue = true
    children
      .foreach(child => {
        if (continue) {
          child.execute()
          continue &&= child.isComplete
        }
      })
  }
}