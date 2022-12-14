package org.sample
package game.model

import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

trait Rank

object Rank {

  def values: List[Rank] = List(One, Two, Three, Four, Five, Six, Seven)

  implicit class RankUtil(rank: Rank) {
    def value(): Int = rank match {
      case Seven => 6
      case Six   => 5
      case Five  => 4
      case Four  => 3
      case Three => 2
      case Two   => 1
      case One   => 0
    }

    def precedingRank(): Option[Rank] = rank match {
      case Seven => Option(Six)
      case Six   => Option(Five)
      case Five  => Option(Four)
      case Four  => Option(Three)
      case Three => Option(Two)
      case Two   => Option(One)
      case One   => Option.empty
    }
  }

  final case object Seven extends Rank

  final case object Six extends Rank

  final case object Five extends Rank

  final case object Four extends Rank

  final case object Three extends Rank

  final case object Two extends Rank

  final case object One extends Rank

  implicit val rankEncoder: Encoder[Rank] = rank => Encoder[String].apply(rank.toString)
  implicit def rankDecoder: Decoder[Rank] = (c: HCursor) => c.downField("rank").as[String]
    .flatMap {
      case "Seven" => Right(Seven)
      case "Six"   => Right(Six)
      case "Five"  => Right(Five)
      case "Four"  => Right(Four)
      case "Three" => Right(Three)
      case "Two"   => Right(Two)
      case "One"   => Right(One)
      case name    => Left(DecodingFailure(s"$name is not a valid Rank", c.history))
    }
}
