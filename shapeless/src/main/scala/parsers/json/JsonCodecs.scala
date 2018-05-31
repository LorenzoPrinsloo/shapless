package parsers.json

import parsers.json.Encoders.{JsonEncoder, JsonObjectEncoder}
import parsers.json.JsonADTs._
import shapeless.labelled.FieldType
import shapeless.{::, HList, HNil, LabelledGeneric, Lazy, Witness}

object JsonCodecs {
  import parsers.json.Encoders.JsonEncoder._

  implicit val stringEncoder: JsonEncoder[String] = pure(str => JsonString(str))
  implicit val numberEncoder: JsonEncoder[Double] = pure(num => JsonNumber(num))
  implicit val intEncoder: JsonEncoder[Int] = pure(int => JsonInt(int))
  implicit val bigDecimalEncoder: JsonEncoder[BigDecimal] = pure(bd => JsonBigDecimal(bd))
  implicit val boolEncoder: JsonEncoder[Boolean] = pure(bool => JsonBoolean(bool))

  implicit def  optionEncoder[A](implicit encoder: JsonEncoder[A]): JsonEncoder[Option[A]] =
    pure(opt => opt.map(encoder.encode).getOrElse(JsonNull))

  implicit def listEncoder[A](implicit encoder: JsonEncoder[A]): JsonEncoder[List[A]] =
    pure(list => JsonArray(list.map(encoder.encode)))

  implicit val hnilEncoder: JsonObjectEncoder[HNil] =
    pureObj(_ => JsonObject(Nil))

  implicit def hlistEncoder[K <: Symbol, H, T <: HList]
  (implicit witness: Witness.Aux[K], hEnc: Lazy[JsonEncoder[H]], tEnc: JsonObjectEncoder[T])
  : JsonObjectEncoder[FieldType[K, H] :: T] = pureObj {
    case head :: tail =>
      val hField = witness.value.name -> hEnc.value.encode(head)
      val tFields = tEnc.encode(tail).fields
        JsonObject(hField :: tFields)
  }

  implicit def genericEncoder[A, R](implicit lgen: LabelledGeneric.Aux[A, R], enc: Lazy[JsonEncoder[R]]): JsonEncoder[A] =
    pure(a => enc.value.encode(lgen.to(a)))
}
