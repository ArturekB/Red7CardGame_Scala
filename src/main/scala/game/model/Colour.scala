package org.sample
package game.model

import io.circe.{Decoder, DecodingFailure, Encoder, HCursor}

trait Colour

object Colour {

  def values: List[Colour] = List(Violet, Indigo, Blue, Green, Yellow, Orange, Red)

  implicit class ColorUtil(colour: Colour) {
    def value: Int = colour match {
      case Red    => 6
      case Orange => 5
      case Yellow => 4
      case Green  => 3
      case Blue   => 2
      case Indigo => 1
      case Violet => 0
    }
  }

  final case object Red extends Colour

  final case object Orange extends Colour

  final case object Yellow extends Colour

  final case object Green extends Colour

  final case object Blue extends Colour

  final case object Indigo extends Colour

  final case object Violet extends Colour

  implicit val colourEncoder: Encoder[Colour] = colour => Encoder[String].apply(colour.toString)
  implicit def colourDecoder: Decoder[Colour] = (c: HCursor) => {
    c.downField("colour").as[String].flatMap {
      case "Red"    => Right(Red)
      case "Orange" => Right(Orange)
      case "Yellow" => Right(Yellow)
      case "Green"  => Right(Green)
      case "Blue"   => Right(Blue)
      case "Indigo" => Right(Indigo)
      case "Violet" => Right(Violet)
      case name     => Left(DecodingFailure(s"$name is not a valid Colour", c.history))
    }
  }
}
