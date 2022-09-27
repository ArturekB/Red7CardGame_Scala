package org.sample
package game.model

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class Game(table: Table, players: List[Player])

case class GameId(value: String)

object GameId{
  implicit val gameIdEncoder: Encoder[GameId] = deriveEncoder[GameId]
}

object Game{
  implicit val gameEncoder: Encoder[Game] = deriveEncoder[Game]
}
