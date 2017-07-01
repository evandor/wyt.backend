package wyt.backend.server

import akka.http.scaladsl.server.Route

trait ApplicationRoutesProvider {
  def routes(): List[Route]
}