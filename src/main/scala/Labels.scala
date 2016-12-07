
//Classified

import scala.collection.mutable._

object Labels {

  var stream: Stream[Int] = Stream.from(1)

  val msgMap = LinkedHashMap[String, MutableList[String]]()

  val dataMsgMap = LinkedHashMap[String, MutableList[String]]()

  def getStreamHead: Int = {
    val head = stream.head
    stream = stream.tail
    head
  }

  def getLabel(str: String): (String, String) = {
    val head = getStreamHead
    (str ++ head.toString() ++ "start", str ++ head.toString() ++ "end")
  }

  def getIfThenElseLabels: (String, String) = {
    val head = getStreamHead
    (s"if_stat${head}_else", s"if_stat${head}_end")
  }

  def getIfThenLabel: String = {
    val head = getStreamHead
    s"if_stat${head}_end"
  }

  def getWhileLabels: (String, String) = {
    val head = getStreamHead
    (s"while_loop${head}_start", s"while_loop${head}_end")
  }

  def addMessageLabel(str: String): Unit = {
    val head = getStreamHead
    addMessageLabel(str, head.toString)
  }

  def addMessageLabel(str: String, name: String): Unit = {
    msgMap += ("msg_" + name ->
      MutableList[String](s".word ${getSize(str)}", f""".ascii \"$str\""""))
  }

  def getMessageLabel: String = {
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
      MutableList[String](".word " + getSize(str), ".ascii \"" + str + "\""))
    // TODO: make sure str is below n characters
  }

  def getDataMsgLabel: String = {
    dataMsgMap.last._1
  }

  def getDataMsgLabel(str: String): MutableList[String] = {
    dataMsgMap.getOrElse(str, throw new RuntimeException(s"$str is not a data" +
      s" message"))
  }

  def printMsgMap(): List[Instruction] = {

    if (msgMap.isEmpty) {
      Nil
    } else {
      LabelData(msgMap.map(pair => pair._1 + ":\n" +
        pair._2.map(_ + "\n").mkString + "\n").mkString) :: Nil
    }
  }

  def printDataMsgMap(): List[Instruction] = {

    if (dataMsgMap.isEmpty) {
      Nil
    } else {
      LabelData(dataMsgMap.map(pair => pair._1 + ":\n" +
        pair._2.map(_ + "\n").mkString + "\n").mkString) :: Nil
    }
  }

  def getSize(str: String): Int = {
    var count: Int = 0
    for (s <- str.toCharArray) {
      if (s != '\\') count += 1
      // todo: this won't work when we have escaped backslashes in the string?
      // todo: fix this
      // is there a reason why we don't just use str.size where appropriate?
    }
    count
  }


}
