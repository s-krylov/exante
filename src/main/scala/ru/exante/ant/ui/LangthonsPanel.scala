package ru.exante.ant.ui

import java.awt.{Rectangle, Color, Graphics, Dimension}

import ru.exante.ant.model.{Cell2, Cell2Model}
import javax.swing.JPanel


class LangthonsPanel extends JPanel with CellInfoStore {

  import ru.exante.ant.ui.LangthonsPanel._

  private var currentDimensionX = INITIAL_DIMENSION_X
  private var currentDimensionY = INITIAL_DIMENSION_Y

//  private val colors = Array(Color.WHITE, Color.RED)
  private val colors = Array(Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.BLACK)

  private val model = new Cell2Model

  def addCellInfo(cell: Cell2, state: Int) = {
    model.addCell(cell, state)
    repaint()
  }

  private def getCellRectangle(cell: Cell2) = {
    val dim = getSize()
    val columnWidth = dim.width / currentDimensionX
    val rowHeight = dim.height / currentDimensionY
    val x = (dim.width - columnWidth) / 2 + columnWidth * cell.x
    val y = (dim.height - rowHeight) / 2 + rowHeight * cell.y
    new Rectangle(x, y, columnWidth, rowHeight)
  }

  private def paintCell(g: Graphics, cell: Cell2, state: Int) = {
    val rectangle = getCellRectangle(cell)

    g.setColor(colors(state))

    g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
    g.setColor(BORDER_COLOR)
    g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height)
  }

  private def scale() = {
    val dim = getSize()
    val needScale = model.getData.toList.exists {
      a =>
        val (cell, _) = a
        val rectangle = getCellRectangle(cell)
        rectangle.x < 0 || (rectangle.x + rectangle.width > dim.width) || rectangle.y < 0 ||
          (rectangle.y + rectangle.height > dim.height)
    }
    if (needScale) {
      currentDimensionX *= SCALE_STEP
      currentDimensionY *= SCALE_STEP
    }
    needScale
  }


  override def paint(g: Graphics): Unit = {
    val dim = getSize()
    g.clearRect(0, 0, dim.width, dim.height)
    scale()

    // draw cells
    model.getData.toList.foreach {
      a =>
        val (cell, state) = a
        paintCell(g, cell, state)
    }
  }
}

object LangthonsPanel {

  private val INITIAL_DIMENSION_X = 11

  private val INITIAL_DIMENSION_Y = 11

  private val SCALE_STEP = 2

  private val BORDER_COLOR = new Color(212, 212, 212)

}
