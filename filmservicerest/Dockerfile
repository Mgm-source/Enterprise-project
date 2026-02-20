FROM maven:3.9.9-eclipse-temurin-21 AS buildncache

WORKDIR /App

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre 

WORKDIR /App

COPY --from=buildnCache /App/target/FilmServiceREST-0.0.1-SNAPSHOT.jar FilmserviceRest.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","FilmserviceRest.jar"]
