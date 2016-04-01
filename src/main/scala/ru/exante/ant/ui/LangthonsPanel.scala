package ru.exante.ant.ui

import java.awt.{Color, Graphics, Dimension}

import ru.exante.ant.model.{Cell2, Cell2Model}
import javax.swing.JPanel


class LangthonsPanel extends JPanel with CellInfoStore {

  import ru.exante.ant.ui.LangthonsPanel._

  private var currentDimensionX = INITIAL_DIMENSION_X
  private var currentDimensionY = INITIAL_DIMENSION_Y

//  private val colors = Array(Color.WHITE, Color.RED)
  private val colors = Array(Color.WHITE, Color.RED, Color.GREEN, Color.BLUE)

  private val model = new Cell2Model

  def addCellInfo(cell: Cell2, state: Int) = {
    model.addCell(cell, state)
    repaint()
  }

  def paintCell(g: Graphics, cell: Cell2, state: Int) = {
    val dim = getSize()
    val columnWidth = dim.width / currentDimensionX
    val rowHeight = dim.height / currentDimensionY

    g.setColor(colors(state))

    val x = (dim.width - columnWidth) / 2 + columnWidth * cell.x
    val y = (dim.height - rowHeight) / 2 + rowHeight * cell.y

    g.fillRect(x, y, columnWidth, rowHeight)
    g.setColor(new Color(212, 212, 212))
    g.drawRect(x, y, columnWidth, rowHeight)
  }

  def fixRelativeZeroElement(i: Int) = {
    if (i == 0) -1
    else if (i < 0) -1 + i
    else i
  }

  override def paint(g: Graphics): Unit = {

    model.getData.toList.foreach {
      a =>
        val (cell, state) = a
        paintCell(g, cell, state)
    }
    // todo scaling
  }
}

object LangthonsPanel {

  private val INITIAL_DIMENSION_X = 11

  private val INITIAL_DIMENSION_Y = 11

}
