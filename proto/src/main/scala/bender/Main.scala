package bender

import jline._
import akka.actor.Actor._
import akka.actor._
import java.io.File
import org.clapper.classutil.ClassFinder
import akka.actor.{ActorRef, Actor}
import plugin.{AnswerMessage, InputMessage}


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


  def activatePlugins(): Unit = {
    searchPlugins foreach {
      pluginString =>

        try {
        println("registring : " + pluginString.name)
        val pluginclass = Class.forName(pluginString.name).asInstanceOf[Class[Actor]]
        val actorref = actorOf(pluginclass)
        actorref.start()
        } catch  {
          case e =>  println(e)
           // What to do ?
      }


    }

  }


  def searchPlugins() = {
    val classpath = List(".").map(new File(_))

    val finder = ClassFinder(classpath)
    val classes = finder.getClasses
    val classMap = ClassFinder.classInfoMap(classes)
    ClassFinder.concreteSubclasses("bender.plugin.Plugin", classMap)
  }

}


class MessageReceiverActor extends Actor {
  def receive = {
    case ":quit" => System.exit(0)
    case s: String => {
      val o = InputMessage(s.hashCode(), s ,this.self)
      val listActor = registry.actorsFor[bender.plugin.Plugin]
      listActor.par.map {a => a ! o}
    }
    case o:AnswerMessage => {
      println(o.answer)

    }
  }
}

