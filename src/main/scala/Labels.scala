import scala.collection.mutable._

object Labels {

  var stream: Stream[Int] = Stream.from(1)

  val msgMap = HashMap[String, MutableList[String]]()
  // val msgs = MutableList[String]()

  def getLabel(str:String): (String, String) = {

    val head = stream.head
    stream = stream.tail

    (str ++ head.toString() ++ "start ", str ++ head.toString() ++ "end")
  }

  def addMessageLabel(str: String): Unit = {
    msgMap += ("msg_" + str ->
      MutableList[String](".word " + str.toCharArray.size, ".ascii \"" + str + "\""))
  }

  def getMessageLabel(): String = {
    msgMap.head._1
  }
}
