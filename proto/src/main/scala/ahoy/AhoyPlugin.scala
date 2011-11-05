package bender.ahoy

import scala.Some
import bender.plugin.{AnswerMessage, InputMessage, SimplePlugin}


class AhoyPlugin extends SimplePlugin {
  val name  = "Ahoy !"

  def process(i:InputMessage)  = {
    i.question match {
      case "Ahoy !" => Some(AnswerMessage(i.id, "Ahoy !", None))
      //case _ => Some(AnswerMessage(i.id, "Bite my shinny ass !", None))
      case _ => None
    }
  }
}


class MdFindPlugin extends SimplePlugin {

  val name = "mdfind"
  def process(i:InputMessage) = {
    import sbt.Process._

    val cToInt = (s:String ) => s.substring(0,s.length()-1).toInt


    val count: Int = cToInt("""mdfind -name -count """"+ i.question + '"' !!)
    val files : Stream[String] = """mdfind -name """"+ i.question + '"' lines_!
    val linesToReturn = scala.math.min(4,count)
    var xs = new Array[String](linesToReturn)
   files.copyToArray(xs,0, linesToReturn)


    Some(AnswerMessage(i.id, "There are "+count +" files for that :"  + "\n" + xs.mkString("\n") + "\n ...", None))
  }
}
