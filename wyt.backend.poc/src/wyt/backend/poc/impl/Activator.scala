package wyt.backend.poc.impl

import domino.DominoActivator
import wyt.backend.poc.WytBackendApp
import org.slf4j.LoggerFactory
import wyt.backend.server.ApplicationRoutesProvider

class Activator extends DominoActivator {

  private var log = LoggerFactory.getLogger(this.getClass)

  whenBundleActive {
    log info "Wyt Backend POC Bundle active now"
    new WytBackendApp().providesService[ApplicationRoutesProvider]
  }
}