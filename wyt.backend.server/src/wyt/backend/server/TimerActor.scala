//package wyt.backend.server
//
//import akka.actor.ActorRef
//import akka.actor.Actor
//import java.util.Date
//import wyt.backend.server.TimerActor._
//import akka.actor.Props
//import org.slf4j.LoggerFactory
//import akka.http.scaladsl.model.HttpResponse
//
//object TimerActor {
//  def props(nextActor: ActorRef) = Props(new TimerActor(nextActor))
//  case class RequestEvent(sender: ActorRef, response: HttpResponse)
//}
//
//class TimerActor(nextActor: ActorRef) extends Actor {
//  
//  private val log = LoggerFactory.getLogger(this.getClass)
//  
//  def receive = start
//  
//  val startingAt = System.currentTimeMillis()
//  var stoppingAt = System.currentTimeMillis()
//  
//  def start: Actor.Receive = {
//    case RequestEvent(_, _) => {
//      context.become(running)
//      nextActor ! TimerActor.RequestEvent(sender, HttpResponse(200))
//    }
//    case _ => log warn "did not understand message"
//  }
//  
//  def running: Receive = {
//    case RequestEvent(_, _) => {
//      stoppingAt = System.currentTimeMillis()
//      context.become(running)
//      nextActor ! TimerActor.RequestEvent(sender, HttpResponse(201))
//    }
//  }
//}