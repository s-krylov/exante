package ru.exante.ant.model

sealed trait Direction {
  val left: Direction
  val right: Direction
}

case class Top() extends Direction {lazy val left = Left(); lazy val right = Right()}
case class Left() extends Direction {lazy val left = Bot(); lazy val right = Top()}
case class Bot() extends Direction {lazy val left = Right(); lazy val right = Left()}
case class Right() extends Direction {lazy val left = Top(); lazy val right = Bot()}