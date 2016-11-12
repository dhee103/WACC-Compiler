

/**
  * Created by dsg115 on 12/11/16.
  */
class IdentNode(val _name: String) extends ExprNode with AssignmentLeftNode {

  val name: String = _name


  var typeVal: TypeNode = _

  override def equals(that: Any): Boolean = that match {

    case that: IdentNode => that.name == this.name && that.hashCode == this.hashCode
    case _ => false
  }

  override def hashCode: Int = {

    val prime = 67

    var result: Int = 1

     result = prime * (result + name.length)

     result = result * prime + (if (name == null) 0 else name.hashCode)

     result
  }

}
