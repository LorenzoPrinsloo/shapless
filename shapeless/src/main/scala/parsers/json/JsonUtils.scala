package parsers.json

object JsonUtils {

  def createEncoder[A](func: A => Map[String, String]): JsonEncoder[A] = (value: A) => func(value)

  def parseJson[A](value: A)(implicit enc: JsonEncoder[A]): String = {

    val encoded = enc.encode(value)

    s"""{${encoded.foldRight("")((b, acc) => {s"'${b._1}' : ${b._2}\n" + acc})}}"""
  }
}
