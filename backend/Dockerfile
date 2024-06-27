FROM eclipse-temurin:21

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests -P prod

ENTRYPOINT [ "java", "-jar", "target/boraPagar-0.0.1-SNAPSHOT.jar" ]
