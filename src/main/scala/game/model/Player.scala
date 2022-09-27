package org.sample
package game.model

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class Player(playerId: PlayerId, name: String, hand: List[Card])

object Player {
  implicit val playerEncoder: Encoder[Player] = deriveEncoder[Player]
}