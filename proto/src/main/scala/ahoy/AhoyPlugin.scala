package bender.ahoy

import bender.plugin.{AnswerMessage, InputMessage, SimplePlugin}
import scala.Some


class AhoyPlugin extends SimplePlugin {
  val name  = "Ahoy !"

  def process(i:InputMessage)  = {
    i.question match {
      case "Ahoy !" => Some(AnswerMessage(i.id, "Ahoy !", None))
      case _ => None
    }
  }
}