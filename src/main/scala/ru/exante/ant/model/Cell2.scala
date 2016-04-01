package ru.exante.ant.model


case class Cell2(x: Int = 0, y: Int = 0) {
  def top = Cell2(x, y - 1)
  def left = Cell2(x - 1, y)
  def bot = Cell2(x, y + 1)
  def right = Cell2(x + 1, y)
}
