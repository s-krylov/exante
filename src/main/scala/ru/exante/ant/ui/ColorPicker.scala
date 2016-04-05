package ru.exante.ant.ui

import java.awt._
import java.awt.event.{MouseEvent, MouseAdapter}
import javax.swing.{Icon, BorderFactory, JColorChooser, JLabel}


class ColorPicker(var color: Color = Color.WHITE) extends JLabel {

  setSize(40, 40)
  setIcon(new Icon {

    override def getIconHeight: Int = {
      40
    }

    override def paintIcon(c: Component, g: Graphics, x: Int, y: Int): Unit = {
      g.setColor(color)
      g.fillRect(0, 0, getIconWidth(), getIconHeight)
    }

    override def getIconWidth: Int = {
      40
    }

  })
  setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE))

  addMouseListener(new MouseAdapter{
    override def mouseEntered(e: MouseEvent): Unit = {
      setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
    }

    override def mouseClicked(e: MouseEvent): Unit = {
      color = JColorChooser.showDialog(ColorPicker.this, "choose color for transition", color)
      repaint()
    }
  })
}
