package parsers.csv

trait CsvEncoder[A] {

  def encode(value: A): List[String]
}
