package bender

import jline._

object Main extends App {
  println("Hello World")



  val terminal = Terminal.getTerminal
  val console = new ConsoleReader()
  terminal.beforeReadLine(console, "B>", null)


  while (true) {

    println(console.readLine())
  }

}

