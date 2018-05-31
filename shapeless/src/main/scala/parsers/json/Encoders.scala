package parsers.json

import parsers.json.JsonADTs.{Json, JsonObject}

object Encoders {

  /**
    * JsonEncoder trait and Helper object for singular items excluding objects
    * @tparam A item to be encoded to json
    */
  trait JsonEncoder[A] {

    def encode(value: A): Json
  }
  object JsonEncoder {

    def pure[A](func: A => Json): JsonEncoder[A] = new JsonEncoder[A] {
      def encode(value: A): Json = func(value)
    }

    def pureObj[A](func: A => JsonObject): JsonObjectEncoder[A] = new JsonObjectEncoder[A] {
      def encode(value:  A): JsonObject = func(value)
    }
  }

  /**
    * JsonObjectEncoder Specifically for encoding nested json objects
    * @tparam A item to be encoded to json
    */
  trait JsonObjectEncoder[A] extends JsonEncoder[A] {
    def  encode(value: A): JsonObject
  }

}
