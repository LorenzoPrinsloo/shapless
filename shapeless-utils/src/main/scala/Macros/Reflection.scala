package Macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

object Reflection {

  def getName(x: Any): String = macro impl

  def impl(c: Context)(x: c.Tree): c.Tree = {
    import c.universe._
    val p = x match {
      case Select(_, TermName(s)) => s
      case _ => ""
    }
    q"$p"
  }
}
