package wyt.backend

import akka.event.LogSource
import akka.osgi.ActorSystemActivator
import com.typesafe.config.Config
import domino.DominoActivator
import domino.capsule.Capsule
import org.osgi.framework.BundleContext
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import domino.service_watching.ServiceWatcherEvent._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import org.slf4j.LoggerFactory

class Activator extends DominoActivator {
  
}