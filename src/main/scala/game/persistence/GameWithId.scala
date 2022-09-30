package org.sample
package game.persistence

import game.model.Game

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class GameWithId(id: String, game: Game)

object GameWithIdUtils{
    implicit val gameEncoder: Encoder[GameWithId] = deriveEncoder[GameWithId]
}
