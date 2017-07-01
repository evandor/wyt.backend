package wyt.backend.poc

import akka.http.scaladsl.server.Route
import wyt.backend.server.ApplicationRoutesProvider
import akka.actor.{ Actor, ActorSystem, Props, ActorLogging }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.{ PathMatcher, Route }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, StatusCodes }
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout

class WytBackendApp extends ApplicationRoutesProvider {
  def routes(): List[Route] = {
    val route = path("root") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }
    List(route)
  }
}