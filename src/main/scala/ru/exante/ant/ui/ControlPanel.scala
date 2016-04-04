package ru.exante.ant.ui

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Insets, GridBagConstraints, GridBagLayout}
import javax.swing._
import javax.swing.event.{ChangeEvent, ChangeListener}


class ControlPanel extends JPanel(new GridBagLayout) {

  private val startBtn = new JToggleButton("start")
  private val addBtn = new JButton("+")
  private val dataPanel = new JPanel(new GridBagLayout)
  private val buttonPanel = new JPanel(new GridBagLayout)

  setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.WHITE))

  private val panelGbc = new GridBagConstraints
  panelGbc.fill = GridBagConstraints.BOTH
  panelGbc.gridwidth = GridBagConstraints.REMAINDER
  panelGbc.weightx = 1
  panelGbc.weighty = 1

  private val buttonPanelGbc = new GridBagConstraints
  panelGbc.fill = GridBagConstraints.BOTH
  panelGbc.gridwidth = GridBagConstraints.REMAINDER
  panelGbc.weightx = 1

  add(dataPanel, panelGbc)
  add(buttonPanel, buttonPanelGbc)

  private val commonGbc = new GridBagConstraints
  commonGbc.anchor = GridBagConstraints.WEST
  commonGbc.insets = new Insets(5, 10, 5, 0)

  buttonPanel.add(startBtn, commonGbc)
  buttonPanel.add(addBtn, commonGbc)

  private val eventshandler = new EventsHandler
  
  addBtn.addActionListener(eventshandler)
  startBtn.addActionListener(eventshandler)

  private def addRow() = {
    val panel = new JPanel(new GridBagLayout)
    
    val group = new ButtonGroup
    val left = new JRadioButton("Left", true)
    val right = new JRadioButton("Right")
    group.add(left)
    group.add(right)
    panel.add(left, commonGbc)
    panel.add(right, commonGbc)

    val delBtn = new JButton("-")
    val delGbc = new GridBagConstraints()
    delGbc.anchor = GridBagConstraints.WEST
    delGbc.insets = new Insets(5, 10, 5, 0)
    delGbc.gridwidth = GridBagConstraints.REMAINDER
    panel.add(delBtn, delGbc)

    val colorChooser = new JTextField
    colorChooser.setColumns(10)
    panel.add(colorChooser, commonGbc)

    val gbc = new GridBagConstraints
    gbc.fill = GridBagConstraints.HORIZONTAL
    gbc.anchor = GridBagConstraints.NORTHWEST
    gbc.gridwidth = GridBagConstraints.REMAINDER
    dataPanel.add(panel, gbc)
  }
  
  private[ControlPanel] class EventsHandler extends ActionListener with ChangeListener {
    
    override def actionPerformed(e: ActionEvent): Unit = {
      if (e.getSource == startBtn) {
        addRow()
        println(e.getSource.asInstanceOf[JToggleButton].isSelected)
      } else if (e.getSource == addBtn) {
        println(e)
      }
    }

    override def stateChanged(e: ChangeEvent): Unit = {
      val selected = e.getSource.asInstanceOf[JToggleButton].isSelected
      println(e)
    }
  }
}
