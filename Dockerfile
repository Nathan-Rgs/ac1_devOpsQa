FROM openjdk:8-jre-slim
WORKDIR /schoolGame
COPY target/*.jar /schoolGame/pratica05.game-0.0.1-SNAPSHOT.jar
EXPOSE 9090
CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=9090 -jar pratica05.game-0.0.1-SNAPSHOT.jar
