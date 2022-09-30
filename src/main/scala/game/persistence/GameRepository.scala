package org.sample
package game.persistence

import game.model.Game

import java.util.UUID
import scala.collection.mutable.ListBuffer

final class GameRepository(private var repo: ListBuffer[GameWithId]) {

  def makeId: String = UUID.randomUUID().toString

  def storeGame(game: Game): String = {
    val id = makeId
    repo += GameWithId(id, game)
    id
  }

  def updateGame(game: GameWithId): Unit = {
    repo -= game
    repo += game
  }

  def deleteGame(game: GameWithId): Unit =
    repo -= game

  def readGame(id: String): Option[GameWithId] =
    repo.find(_.id == id)
}

object GameRepository {
  val repository = new GameRepository(ListBuffer())
}
