package bender.plugin

import akka.actor._


object Plugin {
  def maxConfiance(answers: List[AnswerMessage]): Option[Confiance] = {
    val s = (for (o <- answers; c <- o.confiance) yield c).sorted.reverse
    s.headOption
  }

  def selectMessage(answers: List[AnswerMessage]): Option[AnswerMessage] = {
    // Récupération du maximum de confiance dans les résultats disponibles
    val tmpMaxConfiance: Option[Confiance] = maxConfiance(answers)

    //selectionne le mellieur message
    answers.filter(o => o.confiance == tmpMaxConfiance).headOption

  }

}

trait Plugin extends akka.actor.Actor {

  def name: String

}

abstract class SimplePlugin extends Plugin {

  def process(m: InputMessage): Option[AnswerMessage]

  final def receive = {
    case i: InputMessage => process(i) map (o => i.callback ! o)
  }
}

case class InputMessage(id: Int, question: String, callback: ActorRef)

case class AnswerMessage(id: Int, answer: String, confiance: Option[Confiance])


case class Confiance(i: Int, usefullness: Int) extends Ordered[Confiance] {
  def compare(that: Confiance): Int = {
    val c1 = this
    val c2 = that
    if (c1.usefullness == c2.usefullness) {
      c1.i compare c2.i
    } else {
      c1.usefullness compare c2.usefullness
    }

  }
}

abstract class SuperSimplePluginForJava extends Plugin {

  def process(s: String): String

  final def receive = {

    case i: InputMessage => {

      var answer = process(i.question)

      if (answer != null) {
        i.callback ! AnswerMessage(i.id, answer, None)
      }

    }
  }

}
