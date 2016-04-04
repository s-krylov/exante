package ru.exante.ant.model

import scala.collection.immutable.HashMap

class Cell2Model {

  private var current = Cell2()

  private var data = HashMap(current -> 0)

  def currentCell = current

  def currentSate = getState(current)

  def addCell(cell: Cell2, state: Int = 0): Unit = {
    data += cell -> state
    current = cell
  }

  def incrementCurrentCellState(divisor: Int) = {
    val newState = (currentSate + 1) % divisor
    addCell(current, newState)
    newState
  }

  def getState(cell: Cell2):Int = data.getOrElse(cell, 0)

  def getData = data
}
