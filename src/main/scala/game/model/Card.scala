package org.sample.game.model

import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Decoder, Encoder, HCursor}
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

  implicit val cardEncoder: Encoder[Card] = deriveEncoder[Card]
  implicit val cardDecoder: Decoder[Card] = (c: HCursor) => for {
    rank <- c.downField("rank").as[Rank]
    colour <- c.downField("colour").as[Colour]
  } yield Card(rank, colour)
}