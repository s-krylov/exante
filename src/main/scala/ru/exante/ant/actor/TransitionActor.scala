package ru.exante.ant.actor

import javax.swing.SwingUtilities

import akka.actor.Actor
import ru.exante.ant.behavior.{Left, Right}
import ru.exante.ant.model.Ant
import ru.exante.ant.ui.CellInfoStore


class TransitionActor(private val cellInfoStore: CellInfoStore) extends Actor {

//  val directions = Array(Left(), Right())
  val directions = Array(Left(), Left(), Right(), Left())

  private val ant = new Ant(this)

  override def receive: Receive = {
    case MakeNextStep() => launch()
  }

  def launch(): Unit = {

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
