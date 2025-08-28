FROM maven:3.9.6-eclipse-temurin-17 AS BUILD
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*-SNAPSHOT.jar app.jar
EXPOSE 8008
ENTRYPOINT ["java","-jar","/app/app.jar"]