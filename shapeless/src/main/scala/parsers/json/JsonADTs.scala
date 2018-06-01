package parsers.json

object JsonADTs {

  sealed abstract class Json
  final case class JsonObject(fields: List[(String, Json)]) extends Json
  final case class JsonArray(items: List[Json]) extends Json
  final case class JsonString(value: String) extends Json
  final case class JsonNumber(value: Double) extends Json
  final case class JsonInt(value: Int) extends Json
  final case class JsonBigDecimal(value: BigDecimal) extends Json
  final case class JsonBoolean(value: Boolean) extends Json
  case object JsonNull extends Json

  object Json {

    def stringify(json: Json): String = json match {
      case JsonObject(fields) => "{"+ fields.map(stringifyField).mkString(",") +"}"
      case JsonArray(items) => "[" + items.map(stringify).mkString(",") + "]"
      case JsonString(value) => escape(value)
      case JsonNumber(value) => value.toString
      case JsonBigDecimal(value) => value.toString
      case JsonInt(value) => value.toString
      case JsonBoolean(value) => value.toString
      case JsonNull => "null"
    }

    private def stringifyField(field: (String, Json)): String = {
      val (name, value) = field
      s"${escape(name)}:${stringify(value)}"
    }

    private def escape(str: String): String = "\"" + str.replaceAll("\"", "\\\\\\\\\\\"") + "\""

  }
}
