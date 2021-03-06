package Lifecycle

import Debugging._
import bwapi.DefaultBWListener

class Bot() extends DefaultBWListener {

  override def onStart() {
    try {
      With.onStart()
      With.history.onStart()
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)}
  }

  override def onFrame() {
    try {
      With.performance.startFrame()
      With.onFrame()
      With.tasks.run()
      With.performance.endFrame()
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)
    }
  }

  override def onUnitComplete(unit: bwapi.Unit) {
    try {
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)}
  }

  override def onUnitDestroy(unit: bwapi.Unit) {
    try {
      With.units.onUnitDestroy(unit)
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)}
  }

  override def onUnitDiscover(unit: bwapi.Unit) {
    try {
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)}
  }
  
  override def onUnitHide(unit: bwapi.Unit) {
    try {
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)}
  }
  
  override def onEnd(isWinner: Boolean) {
    try {
      With.history.onEnd(isWinner)
      Manners.onEnd(isWinner)
      With.onEnd()
    }
    catch { case exception: Exception =>
      val dontLoseTheExceptionWhileDebugging = exception
      val dontLoseTheStackTraceWhileDebugging = exception.getStackTrace
      With.logger.onException(exception)}
  }
  
  override def onSendText(text: String) {
    KeyboardCommands.onSendText(text)
  }
}
