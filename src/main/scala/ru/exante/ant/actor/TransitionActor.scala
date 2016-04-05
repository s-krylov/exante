package ru.exante.ant.actor

import javax.swing.SwingUtilities

import akka.actor.Actor
import ru.exante.ant.model._
import ru.exante.ant.ui.api.CellInfoStore


class TransitionActor(private val cellInfoStore: CellInfoStore) extends Actor {

  var directions: Array[Direction] = _
//  val directions = Array(Right(), Left(), Right(), Left(), Left())

  private val ant = new Ant(this)

  override def receive: Receive = {
    case MakeNextStep() => launch()
    case SetDirections(list) => directions = list.toArray[Direction]
  }

  private def launch(): Unit = {

    val next = directions(ant.currentCellState)
    if (!ant.turn.isDefinedAt(next)) {
      sys.error("illegal Direction will be skipped")
    } else {
      val (cell, state) = ant.turn(next)
      SwingUtilities.invokeLater(new Runnable {
        override def run(): Unit = cellInfoStore.addCellInfo(cell, state)
      })
    }
  }
}

case class MakeNextStep()
case class SetDirections(list: List[Direction])
