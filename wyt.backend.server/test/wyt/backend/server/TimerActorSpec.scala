//package wyt.backend.server
//
//import akka.testkit.{ TestKit, ImplicitSender }
//import akka.actor.{ Props, Actor, ActorSystem }
//import org.scalatest.WordSpecLike
//
//import akka.util.Timeout
//import scala.concurrent.Await
//import scala.util.{ Success, Failure }
//
//import scala.language.postfixOps
//import akka.http.scaladsl.model.HttpResponse
//
//
//class TimerActorSpec extends TestKit(ActorSystem("testsystem"))
//  with WordSpecLike
//  with ImplicitSender
//  with StopSystemAfterAll {
//
//
//  "A TimerActor" must {
//    "must start its timer when it receives a message the first time" in {
//
//      import akka.pattern.ask
//      import scala.concurrent.duration._
//      implicit val timeout = Timeout(3 seconds)
//      implicit val ec = system.dispatcher
//      
//      val timerActorProps = TimerActor.props(testActor)
//      val filter = system.actorOf(timerActorProps, "timer")
//  
//      val future = filter ? "msg"//TimerActor.RequestEvent(null,HttpResponse(200))
//      future.onComplete {
//        case Failure(_)   => //handle failure
//        case Success(msg) => //handle success
//      }
//
//      Await.ready(future, timeout.duration)
//    }
//
//    
//
//  }
//}
