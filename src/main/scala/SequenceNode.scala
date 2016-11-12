

/**
  * Created by dsg115 on 12/11/16.
  */
class SequenceNode(val _fstStat: StatNode, val _sndStat: StatNode) extends StatNode {

  val fstStat: StatNode = _fstStat
  val sndStat: StatNode = _sndStat

}
