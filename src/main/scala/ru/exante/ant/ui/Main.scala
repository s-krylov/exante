package ru.exante.ant.ui

import java.awt._
import akka.actor.{Cancellable, Props, ActorSystem}
import collection.immutable.List
import ru.exante.ant.model.Direction
import ru.exante.ant.ui.api.ControlPanelListener
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import ru.exante.ant.actor.{SetDirections, MakeNextStep, TransitionActor}
import javax.swing.{WindowConstants, JFrame, SwingUtilities}


object Main {

  def main(args: Array[String]) {

    SwingUtilities.invokeAndWait(new Runnable {

      override def run(): Unit = {
        val frame = new JFrame("Langton's ant")
        frame.setPreferredSize(new Dimension(700, 400))
        frame.setLayout(new GridBagLayout)

        val lPanel = new LangthonsPanel
        val lPanelGbc = new GridBagConstraints()
        lPanelGbc.fill = GridBagConstraints.BOTH
        lPanelGbc.weightx = 1
        lPanelGbc.weighty = 1
        frame.add(lPanel, lPanelGbc)

        val systemActor = ActorSystem("Langthon-ant-system")
        val transitionsActor = systemActor.actorOf(Props(new TransitionActor(lPanel)), "transitions-actor")
        val rPanel = new ControlPanel(new ControlPanelListener {
          var task: Cancellable = _

          override def onStart(data: List[(Direction, Color)]): Unit = {
            val (directions, colors) = data.unzip
            lPanel.colors = colors.toArray[Color]
            transitionsActor ! SetDirections(directions)
            task = systemActor.scheduler.schedule(0 seconds, 200 milliseconds, transitionsActor, MakeNextStep())
          }

          override def onStop: Unit = {
            task.cancel()
          }
        })
        rPanel.setPreferredSize(new Dimension(300, 400))
        rPanel.setMinimumSize(new Dimension(300, 400))
        val rPanelGbc = new GridBagConstraints()
        rPanelGbc.fill = GridBagConstraints.BOTH
        rPanelGbc.weighty = 1
        frame.add(rPanel, rPanelGbc)

        val screen = Toolkit.getDefaultToolkit.getScreenSize
        frame.setBounds((screen.width - 500) / 2,(screen.height - 400) / 2, (screen.width + 500) / 2,(screen.height + 400) / 2)
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.pack()
        frame.setVisible(true)
      }
    })
  }
}
