package org.sample
package game.initialization

import game.model._
import game.persistence.{GameRepository, GameWithId}
import game.initialization.InitializationParameters.InitializationParameters

import scala.util.Random

object GameService {

  val repository: GameRepository = GameRepository.initialize

  def initializeGame(parameters: InitializationParameters): String = {
    repository.storeGame(GameInitializer(parameters).initGame())
  }

  def showGame(id: String): Option[GameWithId] = repository.readGame(id)

  case class GameInitializer(parameters: InitializationParameters){

    def initGame(): Game = {
      val cardsToDeal = prepareCardsDeck().slice(0, parameters.playersNumber * 8).splitAt(parameters.playersNumber * 7)
      val players = preparePlayers(cardsToDeal._1)
      Game(prepareTable(cardsToDeal._2, players), players)
    }

    def prepareCardsDeck(): List[Card] =
      Random.shuffle(for {
        colour <- Colour.values
        rank <- Rank.values
      } yield Card(rank, colour))

    def validateParameters(): Either[String, Unit] = {
      Either.cond(parameters.names.size == parameters.playersNumber, Right((): Unit) , s"Number of players, ${parameters.playersNumber} does not match player names list size ${parameters.names.size}")
    }

    def preparePlayers(hands: List[Card]): List[Player] = {
      parameters.names.zipWithIndex.zip(hands.sliding(7)).map({ case ((name, id), hand) => Player(PlayerId(id), name, hand) })
    }

    def prepareTable(palettes: List[Card], players: List[Player]): Table = {
      Table(Colour.Red, players.map(_.playerId)
        .zip(palettes)
        .groupMap(_._1)(_._2).map(entry => (entry._1,entry._2)).toList)
    }
  }
}
