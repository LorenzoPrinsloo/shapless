package parsers.json

trait JsonEncoder[A] {

  def encode(value: A): Map[String, String]
}
