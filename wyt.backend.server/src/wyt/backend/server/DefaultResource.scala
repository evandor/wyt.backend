package wyt.backend.server

import wyt.backend.server._
import wyt.backend.server.ActorChainDsl._

class DefaultResource extends ResourceDefinition {

  override val chainRoot = (
    classOf[RequestProcessingActor[_]] ==>
    classOf[Timer] ==>
    classOf[Delegator] ==>
    classOf[Worker]).build()

}