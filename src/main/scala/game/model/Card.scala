package org.sample.game.model

import org.sample.game.model.Card.Rank._

object Card {

  implicit def byValue[A <: Card]: Ordering[A] = Ordering.by(e => e.value)

  sealed trait Colour

  sealed trait Rank

  case class Card(rank: Rank, colour: Colour) {

    def value: Int = Rank.value(rank) * 7 + Colour.value(colour)

    def isEven: Boolean =
      rank match {
        case Six | Four | Two           => true
        case One | Three | Five | Seven => false
      }

    def isBelowFour: Boolean =
      rank match {
        case One | Two | Three         => true
        case Four | Five | Six | Seven => false
      }
  }

  object Colour {
    def value(colour: Colour): Int = colour match {
      case Red    => 6
      case Orange => 5
      case Yellow => 4
      case Green  => 3
      case Blue   => 2
      case Indigo => 1
      case Violet => 0
    }

    final case object Red extends Colour

    final case object Orange extends Colour

    final case object Yellow extends Colour

    final case object Green extends Colour

    final case object Blue extends Colour

    final case object Indigo extends Colour

    final case object Violet extends Colour
  }

  object Rank {

    def values: List[Rank] = List(One, Two, Three, Four, Five, Six, Seven)

    def value(rank: Rank): Int = rank match {
      case Seven => 6
      case Six   => 5
      case Five  => 4
      case Four  => 3
      case Three => 2
      case Two   => 1
      case One   => 0
    }

    def precedingRank(currentRank: Rank): Option[Rank] = currentRank match {
      case Seven => Option(Six)
      case Six   => Option(Five)
      case Five  => Option(Four)
      case Four  => Option(Three)
      case Three => Option(Two)
      case Two   => Option(One)
      case One   => Option.empty
    }

    final case object Seven extends Rank

    final case object Six extends Rank

    final case object Five extends Rank

    final case object Four extends Rank

    final case object Three extends Rank

    final case object Two extends Rank

    final case object One extends Rank
  }
}