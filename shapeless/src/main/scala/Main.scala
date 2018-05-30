import parsers.csv.CsvImplicits._
import parsers.csv.CsvUtils
import parsers.json.JsonUtils
import parsers.json.JsonCodecs._

object Main extends App {

  case class Person(name: String, surname: String, age: Int, hasLicense: Boolean, balance: BigDecimal)
  val lorenzo = Person("Lorenzo", "Prinsloo", 22, hasLicense = true, 11000.00)

  println(CsvUtils.writeCsv(List(lorenzo)))



  println(JsonUtils.parseJson(lorenzo))
}
