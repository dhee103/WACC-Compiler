import scala.collection.mutable._

object Labels {

  var stream: Stream[Int] = Stream.from(1)

  val msgMap = LinkedHashMap[String, MutableList[String]]()
  // val msgs = MutableList[String]()

  val dataMsgMap = LinkedHashMap[String, MutableList[String]]()

  def getLabel(str:String): (String, String) = {

    val head = stream.head
    stream = stream.tail

    (str ++ head.toString() ++ "start ", str ++ head.toString() ++ "end")
  }

  def addMessageLabel(str: String): Unit = {
    msgMap += ("msg_" + str ->
      MutableList[String](".word " + str.size, ".ascii \"" + str + "\""))
  }

  def addMessageLabel(str: String, name: String): Unit = {
    msgMap += ("msg_" + name ->
      MutableList[String](".word " + str.size, ".ascii \"" + str + "\""))
  }

  def getMessageLabel(): String = {
    msgMap.last._1
  }

  def addDataMsgLabel(str: String): Unit = {
    dataMsgMap += ("msg_" + str ->
      MutableList[String](".word " + str.size, ".ascii \"" + str + "\""))
      // TODO: make sure str is below n characters
  }

  def addDataMsgLabel(str: String, name: String): Unit = {
    dataMsgMap += ("msg_" + name ->
      MutableList[String](".word " + str.size, ".ascii \"" + str + "\""))
      // TODO: make sure str is below n characters
  }

  def getDataMsgLabel(): String = {
    dataMsgMap.last._1
  }

  def printDataMsgMap(): List[Instruction] = {
    if (dataMsgMap.isEmpty) {
      Nil
    } else {
      Directive(".data\n") :: Label(dataMsgMap.map(pair => pair._1 + "\n" + pair._2.mkString).mkString) :: Nil
    }
  }

}
