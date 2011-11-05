package bender

import jline._
import akka.actor.Actor._
import akka.actor.Actor
import java.io.File
import org.clapper.classutil.ClassFinder

object Main extends App {
  println("Hello World")


  val terminal = Terminal.getTerminal
  val console = new ConsoleReader()
  terminal.beforeReadLine(console, "B>", null)

  val receiveActor = actorOf[MessageReceiverActor].start


  activatePlugins()

  while (true) {
    receiveActor ! console.readLine()
  }


  def activatePlugins() : Unit = {
    searchPlugins foreach {println(_)}

  }


  def searchPlugins() = {
    val finder = ClassFinder ()
    val classes = finder.getClasses
    classes.foreach(println(_))
    val classMap = ClassFinder.classInfoMap (classes)
    ClassFinder.concreteSubclasses ("bender.plugin.SimplePlugin", classMap)
  }

}


class MessageReceiverActor extends Actor {
  def receive = {
    case ":quit" => System.exit(0)
    case s: String => println("receive string : " + s)
  }
}