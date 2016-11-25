import scala.collection.mutable._

object Labels {

  var stream: Stream[Int] = Stream.from(1)

  val msgMap = LinkedHashMap[String, MutableList[String]]()

  val dataMsgMap = LinkedHashMap[String, MutableList[String]]()

  def getLabel(str: String): (String, String) = {

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

  def getMessageLabel(str: String): MutableList[String] = {
    msgMap.getOrElse(str, throw new RuntimeException(s"$str is not a message"))
  }

  def addDataMsgLabel(str: String): Unit = {
    addDataMsgLabel(str, str)
    // TODO: make sure str is below n characters
  }

  def addDataMsgLabel(str: String, name: String): Unit = {
    dataMsgMap += ("msg_" + name ->
      MutableList[String](".word " + str.size, ".ascii \"" + str + "\""))
    // TODO: make sure str is below n characters
  }

  def getDataMsgLabel: String = {
    dataMsgMap.last._1
  }

  def getDataMsgLabel(str: String): MutableList[String] = {
    dataMsgMap.getOrElse(str, throw new RuntimeException(s"$str is not a data" +
      s" message"))
  }

  def printDataMsgMap(): List[Instruction] = {

    if (dataMsgMap.isEmpty) {
      Nil
    } else {
      Directive("data\n") :: LabelData(dataMsgMap.map(pair => pair._1 + ":\n" +
        pair._2.map(_ + "\n").mkString + "\n").mkString) :: Nil
    }
  }

}
