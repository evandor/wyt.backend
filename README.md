# POC for wyt backend based on Scala and Akka.

## Building:

./gradle clean build

## creating executable

./gradlew wyt.backend.poc:export.wyt.backend

## running executable

java -jar wyt.backend.poc/generated/distributions/executable/wyt.backend.jar

then open localhost:8080/root

## Design

two bundles, one providing the HTTP Server, one defining akkt-http routes:

### wyt.backend.server

creates and starts the HTTP Server once another bundle provides at least one route.

Check the Activator class for details.

### wyt.backend.poc

Provides a single route on "/root", returning a little bit of html.

Check the Activator class for details.

