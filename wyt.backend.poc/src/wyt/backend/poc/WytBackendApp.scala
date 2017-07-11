package wyt.backend.poc

import wyt.backend.server.ApplicationRoutesProvider
import akka.http.scaladsl.server.{ Route }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity, StatusCodes }
import wyt.backend.server.ResourceDefinition
import akka.http.scaladsl.server.PathMatcher
import akka.util.Timeout
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.actor.Props
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import akka.pattern.ask
import wyt.backend.server.DefaultResource

class WytBackendApp extends ApplicationRoutesProvider {

  val cnt = new AtomicInteger(0)

  def routes(): List[Route] = {
    val route = path("root") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }
    var route2 = createRoute(PathMatcher("test"), classOf[DefaultResource])
    List(route, route2)
  }

  private def createRoute(appPath: PathMatcher[Unit], cls: Class[_ <: ResourceDefinition[_]]) = {
    path(appPath) {
      get { ctx =>
        {
          implicit val timeout: Timeout = 5.seconds
          implicit val system = ActorSystem()
          implicit val executionContext = system.dispatcher
          implicit val materializer = ActorMaterializer()

          val routeRootActor = system.actorOf(Props.apply(cls), cls.getSimpleName + "-" + cnt.incrementAndGet())

          val bids = (routeRootActor ? ctx).mapTo[String]

          ctx.complete(bids)
        }
      }
    }
  }
}