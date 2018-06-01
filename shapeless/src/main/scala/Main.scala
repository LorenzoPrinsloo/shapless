//import parsers.csv.CsvImplicits._
//import parsers.csv.CsvUtils
import parsers.json.Encoders.JsonEncoder
import parsers.json.JsonADTs.Json
import parsers.json.JsonCodecs._
import shapeless.ops.hlist.Last
import shapeless.{::, Generic, HList, LabelledGeneric, Witness, HNil}

object Main extends App {

  case class Person(name: String, surname: String, age: Int, hasLicense: Boolean, balance: BigDecimal, relatives: List[String])
  val relatives: List[String] = List("Lourens", "Carah", "Lonngren", "Dunestea")
  val lr = Person("Lorenzo", "Prinsloo", 22, hasLicense = true, 11000.00, relatives)

  def encode[A](value: A)(implicit enc: JsonEncoder[A]): Json = enc.encode(value)

  val encoded = encode(lr)
  println(encoded)
  println()
  println(Json.stringify(encoded))
  println()

  println(LabelledGeneric[Person].to(lr))
  println(LabelledGeneric[Person].from(LabelledGeneric[Person].to(lr)))

 val last = Last[String :: String :: Int :: Boolean :: BigDecimal :: List[String] :: HNil]

  trait Second[L <: HList] {
    type Out
    def apply(value: L): Out
  }
  object Second {
    type Aux[L <: HList, O] = Second[L] { type Out = O }

    def apply[L <: HList](implicit inst: Second[L]): Aux[L, inst.Out] = inst
  }

  lr.productIterator.map()
}
