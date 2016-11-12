

/**
  * Created by dsg115 on 12/11/16.
  */
class BoolTypeNode extends BaseTypeNode {
  override def equals(other: Any): Boolean = {
    other.isInstanceOf[BoolTypeNode]
  }
}
