package lorenzo.experimental.Macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

object Reflection {

  def getName(x: Any): String = macro impl

  def impl(c: Context)(x: c.Tree): c.Tree = {
    import c.universe._

    println("Reach Method")

    val p = x match {
      case Select(_, TermName(s)) => {println(s"Select: $s");s}
      case _ => {println("Empty"); ""}
    }
    q"$p"
  }
}
