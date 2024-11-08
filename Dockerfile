FROM eclipse-temurin:17-jre
WORKDIR /schoolGame
COPY target/*.jar /schoolGame/pratica05.game-0.0.1-SNAPSHOT.jar
EXPOSE 8484
CMD ["java", "-XX:+UseContainerSupport", "-Xmx512m", "-Dserver.port=8585", "-jar", "pratica05.game-0.0.1-SNAPSHOT.jar"]
