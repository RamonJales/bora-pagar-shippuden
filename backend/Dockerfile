FROM eclipse-temurin:21

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests -P prod

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "target/borapagar-0.0.1-SNAPSHOT.jar" ]
