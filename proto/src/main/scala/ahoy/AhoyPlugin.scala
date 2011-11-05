package bender.ahoy

import bender.plugin.SimplePlugin
import scala.Some


class AhoyPlugin extends SimplePlugin {
  val name  = "Ahoy !"

  def process(i:InputMessage)  = {
    i.question match {
      case "Ahoy !" => Some(AnswerMessage(i.id, "Ahoy !"))
      case _ => None
    }
  }
}