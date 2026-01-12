# ===========================================
# STEG 1: Bygg applikationen
# ===========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Kopiera endast pom.xml först
# Detta gör att Docker kan återanvända cache för dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

#Kopiera källkoden och bygg projektet
# mvn package kör även tester automatiskt
COPY src ./src
RUN mvn -B package -DskipTests


FROM eclipse-temurin:17-jre

WORKDIR /app

# Kopiera den färdigbyggda jar-filen från build-steget ovan
COPY --from=build /app/target/spring-boot-rest-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

