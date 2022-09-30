package org.sample
package game.initialization

import game.model._

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

import scala.util.Random

case class GameInitializer(parameters: InitializationParameters) {

  def initGame(): Game = {
    val cardsToDeal = prepareCardsDeck()
      .slice(0, parameters.numberOfCardsOnHands + parameters.numberOfPlayers)
      .splitAt(parameters.numberOfCardsOnHands)
    val players = preparePlayers(cardsToDeal._1)
    Game(prepareTable(cardsToDeal._2, players), players)
  }

  def prepareCardsDeck(): List[Card] =
    Random.shuffle(for {
      colour <- Colour.values
      rank <- Rank.values
    } yield Card(rank, colour))

  def preparePlayers(hands: List[Card]): List[Player] = {
    parameters.names.zipWithIndex.zip(hands.sliding(7)).map({ case ((name, id), hand) => Player(PlayerId(id, name), hand) })
  }

  def prepareTable(palettes: List[Card], players: List[Player]): Table = {
    Table(Colour.Red, players.map(_.playerId)
      .zip(palettes)
      .groupMap(_._1)(_._2)
      .toList
    )
  }
}

case class InitializationParameters(names: List[String]) {
  val numberOfPlayers: Int = names.size
  val numberOfCardsOnHands: Int = names.size * 7

  def validateParameters(): Either[String, Unit] = Either.cond(Range(2, 5).contains(numberOfPlayers), Right((): Unit), s"Number of players should be between 2 and 4 but is: $numberOfPlayers")
}

object InitializationParameters {
  implicit val initializationParametersEncoder: Encoder[InitializationParameters] = deriveEncoder[InitializationParameters]
}
