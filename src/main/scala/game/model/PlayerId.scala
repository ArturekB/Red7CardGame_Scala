package org.sample
package game.model

import io.circe.Encoder

case class PlayerId(value: Int)

object PlayerId {

  lazy implicit val playerIdEncoder: Encoder[PlayerId] = id => Encoder[Int].apply(id.value)
}
