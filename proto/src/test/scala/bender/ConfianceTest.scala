package bender

import org.scalatest.FunSuite
import plugin.{Confiance, AnswerMessage}
import scala.Some
import akka.actor.Actor._
import plugin.Plugin._

class ConfianceTest extends FunSuite {


   test("Simple confiance compare") {

     val c1 = Confiance(100,0)
     val c2 = Confiance(50,0)

     assert(c1 > c2 )

     val c3 = Confiance(0,1)

     assert(c3 > c1)


     val c4 =    Confiance(100,3)
     val c5 = Confiance(105,2)

     assert(c4 > c5)
   }


  test("Simple confiance test with message") {

    val o1 = AnswerMessage(0,"Ahoy 1",Some(Confiance(100,3)))
    val lo = List(AnswerMessage(0,"Ahoy", Some(Confiance(0,3))), AnswerMessage(0,"Ahoy",Some( Confiance(105,2))), AnswerMessage(0,"Ahoy",None))

    assert(Some(Confiance(100,3))=== maxConfiance (o1 :: lo))
    assert(Some(o1) === selectMessage(o1 :: lo))

    assert(None === selectMessage(List.empty[AnswerMessage]))
  }



}