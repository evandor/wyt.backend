# POC for wyt backend based on Scala and Akka.

## Building:

./gradle clean build

## creating executable

./gradlew wyt.backend.poc:export.wyt.backend

## running executable

java -jar wyt.backend.poc/generated/distributions/executable/wyt.backend.jar

then open localhost:8080/root

