package org.sample.game.model

import io.circe.Encoder
import org.sample.game.model.Colour._
import org.sample.game.model.Rank._

case class Card(rank: Rank, colour: Colour) {

  val value: Int = rank.value * 7 + colour.value

  val isEven: Boolean =
    rank match {
      case Six | Four | Two           => true
      case One | Three | Five | Seven => false
    }

  val isBelowFour: Boolean =
    rank match {
      case One | Two | Three         => true
      case Four | Five | Six | Seven => false
    }
}

object Card {
  lazy implicit val cardEncoder: Encoder[Card] = Encoder.forProduct2("rank", "colour")(card => (card.rank.toString, card.colour.toString))
}