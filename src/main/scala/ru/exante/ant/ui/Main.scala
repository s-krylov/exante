package ru.exante.ant.ui

import java.awt.{GridBagConstraints, GridBagLayout, Toolkit, Dimension}
import akka.actor.{Props, ActorSystem}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import ru.exante.ant.actor.{MakeNextStep, TransitionActor}
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

//        val rPanel = new ControlPanel
//        rPanel.setPreferredSize(new Dimension(300, 400))
//        rPanel.setMinimumSize(new Dimension(300, 400))
//        val rPanelGbc = new GridBagConstraints()
//        rPanelGbc.fill = GridBagConstraints.BOTH
//        rPanelGbc.weighty = 1
//        frame.add(rPanel, rPanelGbc)

        val systemActor = ActorSystem("Langthon-ant-system")
        val transitionsActor = systemActor.actorOf(Props(new TransitionActor(lPanel)), "transitions-actor")
        systemActor.scheduler.schedule(0 seconds, 200 milliseconds, transitionsActor, MakeNextStep())

        val screen = Toolkit.getDefaultToolkit.getScreenSize
        frame.setBounds((screen.width - 500) / 2,(screen.height - 400) / 2, (screen.width + 500) / 2,(screen.height + 400) / 2)
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.pack()
        frame.setVisible(true)
      }
    })
  }
}
