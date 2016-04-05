package ru.exante.ant.model

import ru.exante.ant.actor.TransitionActor

/**
 * @author krylov
 */
class Ant(private val transitionActor: TransitionActor,
          private var currentDirection: Direction = Top()) {

  private val model = new Cell2Model

  def turn: PartialFunction[Direction, (Cell2 , Int)] = {
    // assume that function can take only Left and Right values
    case Left() =>
      currentDirection = currentDirection.left
      changeCurrentCell()
    case Right() =>
      currentDirection = currentDirection.right
      changeCurrentCell()
  }

  def currentCellState = model.currentSate

  private def changeCurrentCell() = {
    // increment previous state
    val changedCell = model.currentCell
    val state = model.incrementCurrentCellState(transitionActor.directions.length)
    // change cell
    val cell = currentDirection match {
      case Top() => changedCell.top
      case Left() => changedCell.left
      case Bot() => changedCell.bot
      case Right() => changedCell.right
    }
    // check state for new cell
    model.addCell(cell, model.getState(cell))
    (changedCell, state)
  }
}
