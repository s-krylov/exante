package ru.exante.ant.ui

import java.awt.{Insets, GridBagConstraints, GridBagLayout}
import javax.swing.{JRadioButton, ButtonGroup, JPanel}
import ru.exante.ant.model.{Left, Right}


class TransitionPanel extends JPanel(new GridBagLayout) {

  private val group = new ButtonGroup
  private val left = new JRadioButton("Left", true)
  private val right = new JRadioButton("Right")
  group.add(left)
  group.add(right)

  private val commonGbc = new GridBagConstraints
  commonGbc.anchor = GridBagConstraints.WEST
  commonGbc.insets = new Insets(5, 10, 5, 0)

  add(left, commonGbc)
  add(right, commonGbc)

  private val colorChooser = new ColorPicker()
  add(colorChooser, commonGbc)

  def getDirection = {
    if (left.isSelected()) Left() else Right()
  }

  def getColor = {
    colorChooser.color
  }
}
