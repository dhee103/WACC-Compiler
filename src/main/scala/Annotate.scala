import scala.reflect.ClassTag
import scala.reflect.runtime.{universe => univ}

class Annotate(val _AST: ProgNode) {

  private var AST = _AST

  //def getAnnotatedAST

  def annotateStatNode(statement: StatNode): Unit = {

  

  }

  def getType[T](obj: T): String = {
    val str: String = obj.getClass().toString
    str.substring(6, str.length)
  }

  def checkNodesHaveSameType(typeNode1: TypeNode, typeNode2: TypeNode): Boolean = {
    def f[A, B: ClassTag](a: A, b: B) = a match {
      case _: B => true
      case _    => false
    }
    f(typeNode1, typeNode2)
  }



}
