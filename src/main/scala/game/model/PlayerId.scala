package org.sample
package game.model

import io.circe.Encoder

case class PlayerId(value: Int, name: String)

object PlayerId {

  lazy implicit val playerIdEncoder: Encoder[PlayerId] = id => Encoder[String].apply(id.name)
}
