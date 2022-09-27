package org.sample.game

import cats.effect.{ExitCode, IO, IOApp}
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.{EntityDecoder, HttpRoutes}
import org.sample.game.initialization.GameService
import org.sample.game.initialization.InitializationParameters.InitializationParameters
import org.sample.game.model.GameId

import scala.concurrent.ExecutionContext

object Main extends IOApp {

  private val gameService = GameService
  implicit val initializationParametersDecoder: EntityDecoder[IO, InitializationParameters] = jsonOf[IO, InitializationParameters]
  private val route = HttpRoutes.of[IO] {
    case req@POST -> Root / "initialize" =>
      req.as[InitializationParameters] flatMap { parameters =>
        val gameId = GameId(gameService.initializeGame(parameters))
        Ok(gameId)
      }
    case GET -> Root / UUIDVar(gameId) =>
      gameService.showGame(gameId.toString) match {
        case Some(game) => Ok(game)
        case None       => BadRequest(s"Game with id: ${gameId.toString} does not exist")
      }
  }


  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](ExecutionContext.global)
      .bindHttp(port = 9001, host = "localhost")
      .withHttpApp(route.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)


}
//import org.http4s.circe.CirceEntityCodec._
//import org.sample.game.model.Game._
//import org.http4s.dsl.io.{->, /, Created, GET, Ok, POST, Root}
//import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
//import org.http4s.server.blaze.BlazeServerBuilder
//import org.http4s.{HttpRoutes, Response}
//import initialization.GameService
//import org.sample.game.initialization.InitializationParameters.InitializationParameters
//import org.sample.game.initialization.InitializationParameters._
