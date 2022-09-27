package org.sample
package game.model

import io.circe.Encoder

trait Colour

object Colour {

  def values: List[Colour] = List(Violet, Indigo, Blue, Green, Yellow, Orange, Red)

  lazy implicit val colourEncoder: Encoder[Colour] = id => Encoder[String].apply(id.toString)

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

}
