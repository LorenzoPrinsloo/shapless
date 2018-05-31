//import parsers.csv.CsvImplicits._
//import parsers.csv.CsvUtils
import parsers.json.Encoders.JsonEncoder
import parsers.json.JsonADTs.Json
import parsers.json.JsonCodecs._

object Main extends App {

  case class Person(name: String, surname: String, age: Int, hasLicense: Boolean, balance: BigDecimal, relatives: List[String])
  val relatives: List[String] = List("Lourens", "Carah", "Lonngren", "Dunestea")
  val lr = Person("Lorenzo", "Prinsloo", 22, hasLicense = true, 11000.00, relatives)

  def encode[A](value: A)(implicit enc: JsonEncoder[A]): Json = enc.encode(value)

  val encoded = encode(lr)
  println(encoded)
  println()
  println(Json.stringify(encoded))
}
