package bender.plugin

import akka.actor._


trait Plugin extends akka.actor.Actor {

  def name:String




}

trait SimplePlugin extends Plugin {

  def process(m:InputMessage): Option[AnswerMessage]
  final def receive = {
    case i:InputMessage => process (i) map (o => i.callback ! o)
  }
}

case class InputMessage(id: Int ,question: String, callback: ActorRef)
case class AnswerMessage(id: Int, answer: String, confiance: Option[Confiance])

case class Confiance(i: Int, usefullness: Int)
