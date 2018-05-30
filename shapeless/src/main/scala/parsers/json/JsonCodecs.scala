package parsers.json

import com.github.dwickern.macros.NameOf._
import parsers.json.JsonUtils.createEncoder
import shapeless.{::, Generic, HList, HNil}

object JsonCodecs {
  implicit val string: JsonEncoder[String] = createEncoder(str => Map(s"${str.hashCode}" -> s"'$str'"))
  implicit val int: JsonEncoder[Int] = createEncoder(int => Map(s"${int.hashCode}" -> s"$int"))
  implicit val bool: JsonEncoder[Boolean] = createEncoder(bool => Map(s"${bool.hashCode}" -> s"$bool"))
  implicit val bigDec: JsonEncoder[BigDecimal] = createEncoder(bd => Map(s"${bd.hashCode}" -> s"$bd"))
  implicit val hnil: JsonEncoder[HNil] = createEncoder(_ => Map.empty[String, String])

  implicit def hlist[H, T <: HList](implicit hEnc: JsonEncoder[H], tEnc: JsonEncoder[T]): JsonEncoder[H :: T] =
    createEncoder { case h :: t => hEnc.encode(h) ++ tEnc.encode(t)}

  implicit def gen[A, R](implicit g: Generic.Aux[A, R], enc: JsonEncoder[R]): JsonEncoder[A] =
    createEncoder(a => enc.encode(g.to(a)))
}
