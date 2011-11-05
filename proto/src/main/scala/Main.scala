package bender

import jline._
import akka.actor.Actor._
import akka.actor.Actor

object Main extends App {
  println("Hello World")



  val terminal = Terminal.getTerminal
  val console = new ConsoleReader()
  terminal.beforeReadLine(console, "B>", null)

  val receiveActor = actorOf[MessageReceiverActor].start

  while (true) {
    receiveActor ! console.readLine()
  }

}


class MessageReceiverActor extends Actor {
  def receive = {
    case ":quit" =>   System.exit(0)
    case s:String => println("receive string : " + s)
  }
}