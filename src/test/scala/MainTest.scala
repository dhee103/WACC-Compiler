import org.scalatest._

class MainTest extends FlatSpec {
  "factorial(0)" should "return 1" in {
    val r = Main.factorial(0)
    assert(r === 1)
  }
}