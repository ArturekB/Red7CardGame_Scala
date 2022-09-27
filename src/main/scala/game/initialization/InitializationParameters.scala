package org.sample
package game.initialization

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

object InitializationParameters {

  case class InitializationParameters(playersNumber: Int, names: List[String])

  implicit val initializationParametersDecoder: Decoder[InitializationParameters] = deriveDecoder[InitializationParameters]
  implicit val initializationParametersEncoder: Encoder[InitializationParameters] = deriveEncoder[InitializationParameters]
}
