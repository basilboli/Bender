package bender.plugin

import akka.actor._


trait Plugin extends akka.actor.Actor {

  def name:String

}

abstract class SimplePlugin extends Plugin {

  def process(m:InputMessage): Option[AnswerMessage]
  final def receive = {
    case i:InputMessage => process (i) map (o => i.callback ! o)
  }
}

case class InputMessage(id: Int ,question: String, callback: ActorRef)
case class AnswerMessage(id: Int, answer: String, confiance: Option[Confiance])

case class Confiance(i: Int, usefullness: Int)


abstract class SuperSimplePluginForJava extends Plugin {

  def process(s:String) : String

  final def receive = {

    case i:InputMessage => {

      var answer = process(i.question)

      if(answer != null ) {
        i.callback ! AnswerMessage(i.id, answer, None)
      }

    }
  }

}
