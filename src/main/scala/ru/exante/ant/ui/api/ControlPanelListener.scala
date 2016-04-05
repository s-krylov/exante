package ru.exante.ant.ui.api

import java.awt.Color
import ru.exante.ant.model.Direction

trait ControlPanelListener {
  def onStop: Unit
  def onStart(data : List[(Direction, Color)]): Unit
}
