package ru.exante.ant.ui

import ru.exante.ant.model.Cell2


trait CellInfoStore {

  def addCellInfo(cell: Cell2, state: Int): Unit

}
