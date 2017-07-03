package wyt.backend.poc

import wyt.backend.server.ApplicationRoutesProvider
import akka.http.scaladsl.server.{ Route }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, StatusCodes }

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