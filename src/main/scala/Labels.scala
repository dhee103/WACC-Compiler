object Labels {

  var stream: Stream[Int] = Stream.from(1)

  def getLabel(str:String): (String, String) = {

    val head = stream.head
    stream = stream.tail

    (str ++ head.toString() ++ "start ", str ++ head.toString() ++ "end")
  }
}
