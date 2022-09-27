package org.sample
package game.model

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class Table(rule: Colour, palettes: List[(PlayerId, List[Card])])

object Table {

  implicit val tableEncoder: Encoder[Table] = deriveEncoder[Table]
}

