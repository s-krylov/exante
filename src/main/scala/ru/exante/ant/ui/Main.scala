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
        frame.setPreferredSize(new Dimension(500, 400))
        frame.setLayout(new GridBagLayout)
        val gbc = new GridBagConstraints()
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1
        gbc.weighty = 1
        val lPanel = new LangthonsPanel
        frame.add(lPanel, gbc)

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
