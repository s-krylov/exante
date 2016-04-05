package ru.exante.ant.ui

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Insets, GridBagConstraints, GridBagLayout}
import javax.swing._
import javax.swing.event.{ChangeEvent, ChangeListener}

import ru.exante.ant.ui.api.ControlPanelListener


class ControlPanel(val listener: ControlPanelListener) extends JPanel(new GridBagLayout) {

  private var transitionPanels = List[TransitionPanel]()
  private val startBtn = new JToggleButton("start")
  private val addBtn = new JButton("+")
  private val dataPanel = new JPanel(new GridBagLayout)
  private val buttonPanel = new JPanel(new GridBagLayout)

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

  startBtn.setEnabled(false)

  private def addRow() = {
    val panel = new TransitionPanel()

    transitionPanels = panel :: transitionPanels

    val gbc = new GridBagConstraints
    gbc.fill = GridBagConstraints.HORIZONTAL
    gbc.anchor = GridBagConstraints.NORTHWEST
    gbc.gridwidth = GridBagConstraints.REMAINDER
    dataPanel.add(panel, gbc)
    startBtn.setEnabled(true)
    revalidate()
    repaint()
  }
  
  private[ControlPanel] class EventsHandler extends ActionListener {
    
    override def actionPerformed(e: ActionEvent): Unit = {
      if (e.getSource == startBtn) {
        if (startBtn.isSelected) {
          listener.onStart(transitionPanels.reverse.map {
            t => (t.getDirection, t.getColor)
          })
        } else {
          listener.onStop
        }
      } else if (e.getSource == addBtn) {
        addRow()
      }
    }

  }
}
