package wyt.backend.server.impl

import akka.osgi.ActorSystemActivator
import domino.DominoActivator
import domino.capsule.Capsule
import org.osgi.framework.BundleContext
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import scala.concurrent.Future
import domino.service_watching.ServiceWatcherEvent._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import org.slf4j.LoggerFactory
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import wyt.backend.server.ApplicationRoutesProvider

class Activator extends DominoActivator {

  private var log = LoggerFactory.getLogger(this.getClass)

  implicit var theSystem: ActorSystem = _
  var routes = scala.collection.mutable.ListBuffer[Route]()
  var futureBinding: Future[Http.ServerBinding] = _

  private class AkkaCapsule(bundleContext: BundleContext) extends ActorSystemActivator with Capsule {

    override def start(): Unit = start(bundleContext)
    override def stop(): Unit = stop(bundleContext)

    def configure(osgiContext: BundleContext, system: ActorSystem): Unit = {
      log info "Registering Actor System as Service!!"
      registerService(osgiContext, system)
      log info s"ActorSystem [${system.name}] initialized."
      theSystem = system
    }

    override def getActorSystemName(context: BundleContext): String = "SkysailActorSystem"
  }

  whenBundleActive {    
    log info "Server Bundle active now"
    addCapsule(new AkkaCapsule(bundleContext))
    watchServices[ApplicationRoutesProvider] {
      case AddingService(s, context) =>
        if (s == null) {
          log warn "service null"
        } else {
          log info s"Adding routes ${s.routes().map { r => r.toString() }.mkString(",")} from ${s.getClass.getName}"
          routes ++= s.routes()
          restartServer(routes.toList)
        }
      case ModifiedService(s, _) =>
        println("Service modified")
      case RemovedService(s, _) =>
        log info s"Removing routes ${s.routes()} not supplied no more from ${s.getClass.getName}"
        routes --= s.routes()
        restartServer(routes.toList)
    }
  }

  def restartServer(arg: List[Route]) = {
    implicit val materializer = ActorMaterializer()
    if (futureBinding != null) {
      implicit val executionContext = theSystem.dispatcher
      futureBinding.flatMap(_.unbind()).onComplete { _ => futureBinding = startServer(arg) }
    } else {
      futureBinding = startServer(arg)
    }
  }

  private def startServer(arg: List[Route]) = {
    log info "starting server on port 8080"
    implicit val materializer = ActorMaterializer()
    arg.size match {
      case 0 =>
        log warn "Akka HTTP Server not started as no routes are defined"; null
      case 1 => Http(theSystem).bindAndHandle(arg(0), "localhost", 8080)
      case _ => Http(theSystem).bindAndHandle(arg.reduce((a, b) => a ~ b), "localhost", 8080)
    }
  }
}