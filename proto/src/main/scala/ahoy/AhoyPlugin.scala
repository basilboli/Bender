package bender.ahoy

import scala.Some
import bender.plugin.{Confiance, AnswerMessage, InputMessage, SimplePlugin}

class AhoyPlugin extends SimplePlugin {
  val name = "Ahoy !"

  def process(i: InputMessage) = {
    import sbt.Process._
    val user: String = ("whoami" !!).replace("\n","")

    i.question match {
      case "Ahoy !" =>  Some(AnswerMessage(i.id, "Ahoy " + user + " !", Some(Confiance(100, 0))))

      //case _ => Some(AnswerMessage(i.id, "Bite my shinny ass !", None))
      case _ => None
    }
  }
}


class MdFindPlugin extends SimplePlugin {

  val name = "mdfind"

  def process(i: InputMessage) = {
    import sbt.Process._

    val cToInt = (s: String) => s.substring(0, s.length() - 1).toInt

    if(i.question.startsWith(":find")) {
     val question  = i.question.replace(":find", "")

    val count: Int = cToInt("""mdfind -name -count """" + question + '"' !!)
    val files: Stream[String] = """mdfind -name """" + question + '"' lines_!

    if ((count < 20) && (count > 0)) {
      val linesToReturn = count
      var xs = new Array[String](linesToReturn)
      files.copyToArray(xs, 0, linesToReturn)
      Some(AnswerMessage(i.id, "There are " + count + " files for that :" + "\n" + xs.mkString("\n"), None))
    } else {
      None
    }
      } else None
  }


}
