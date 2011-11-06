package bender

import org.scalatest.FunSuite
import plugin.Confiance

class ConfianceTest extends FunSuite {


   test("Simple confiance compare") {

     val c1 = Confiance(100,0)
     val c2 = Confiance(50,0)

     assert(c1 > c2 )

     val c3 = Confiance(0,1)

     assert(c3 > c1)
   }




}