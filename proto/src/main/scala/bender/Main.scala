package bender

import jline._
import akka.actor.Actor._
import akka.actor._
import java.io.File
import org.clapper.classutil.ClassFinder
import akka.actor.{ActorRef, Actor}
import akka.dispatch.Future
import collection.mutable.HashMap
import plugin.{Confiance, AnswerMessage, InputMessage}
import scala.Some
import bender.plugin.Plugin._

object Main extends App {
val benderString = """
        .-.
     ,'     `.
    |_________|
   ,'-.-.--.-.`.
  (( (*  )(*  )))
   `.-`-'--`-'.'
    | ,-.-.-. |
    |(|-|-|-|)|
    |_`-'-'-'_|    What ?  """


  val terminal = Terminal.getTerminal

  val console = new ConsoleReader()
  terminal.beforeReadLine(console, "B>", null)

  val receiveActor = actorOf[MessageReceiverActor].start


  Future {
     activateActors();
    activatePlugins();

  }
       println(benderString)
  while (true) {
    receiveActor ! console.readLine()
  }

  def activateActors(): Unit = {
    val actorref = actorOf[PrintBufferProcessorActor].start
  }

  def activatePlugins(): Unit = {
    searchPlugins foreach {
      pluginString =>

        try {
          // println("registring : " + pluginString.name)
          val pluginclass = Class.forName(pluginString.name).asInstanceOf[Class[Actor]]
          val actorref = actorOf(pluginclass)
          actorref.start()
        } catch {
          case e => println(e)
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
    case ":quit" => {
      registry.actorsFor[bender.plugin.Plugin].foreach(x => x.stop())
      System.exit(0)
    }
    case "" => ()
    case s: String => {
      val o = InputMessage(s.hashCode(), s, registry.actorFor[PrintBufferProcessorActor].head)
      registry.actorFor[PrintBufferProcessorActor].head ! o
      val listActor = registry.actorsFor[bender.plugin.Plugin]
      listActor.par.map {
        a => a ! o
      }

    }

  }
}

class PrintBufferProcessorActor extends Actor {

  var message: Option[InputMessage] = None
  var listOfAnswer: List[AnswerMessage] =  List.empty[AnswerMessage]

  def receive = {
    case o: AnswerMessage => {
      for (m <- message  if o.id == m.id) {
        listOfAnswer = o :: listOfAnswer
      }

    }

    case i: InputMessage => {
         publish()
      message = Some(i)

      Future{Thread.sleep(1500); this.self ! 'publish}

    }

    case 'publish => {
       publish()
    }
  }

  def publish() {

 selectMessage(listOfAnswer). map(o => println(o.answer))

    //purge de la liste des messages
    listOfAnswer = List.empty[AnswerMessage]
  }



}

