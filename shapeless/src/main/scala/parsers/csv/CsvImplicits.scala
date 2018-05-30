package parsers.csv

import parsers.csv.CsvUtils.createEncoder
import shapeless.{::, Generic, HList, HNil}

object CsvImplicits {
  implicit val stringEnc: CsvEncoder[String] = createEncoder(str => List(str))
  implicit val intEnc: CsvEncoder[Int] = createEncoder(num => List(num.toString))
  implicit val boolEnc: CsvEncoder[Boolean] = createEncoder(bool => List(s"$bool"))
  implicit val bigDecEnc: CsvEncoder[BigDecimal] = createEncoder(bigdec => List(bigdec.toString()))
  implicit val hnilEnc: CsvEncoder[HNil] = createEncoder(_ => Nil)

  implicit def hlistEnc[H, T <: HList](implicit hEnc: CsvEncoder[H], tEnc: CsvEncoder[T]): CsvEncoder[H :: T] =
    createEncoder { case h :: t => hEnc.encode(h) ++ tEnc.encode(t) }

  implicit def genEnc[A, R](implicit gen: Generic.Aux[A, R], enc: CsvEncoder[R]): CsvEncoder[A] =
    createEncoder(a => enc.encode(gen.to(a)))
}
