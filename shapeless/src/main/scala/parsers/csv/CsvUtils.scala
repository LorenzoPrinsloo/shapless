package parsers.csv

object CsvUtils {

  def createEncoder[A](func: A => List[String]): CsvEncoder[A] = (value: A) => func(value)

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values.map(value => enc.encode(value).mkString(",")).mkString("\n")
}
