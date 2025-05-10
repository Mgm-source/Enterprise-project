FROM eclipse-temurin:21-jre 

WORKDIR /Application

COPY target/FilmServiceREST-0.0.1-SNAPSHOT.jar FilmserviceRest.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","FilmserviceRest.jar"]
