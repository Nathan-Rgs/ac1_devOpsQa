FROM openjdk:8-jre-slim
WORKDIR /pratica05.game
COPY target/*.war /pratica05.game/pratica05.game-0.0.1-SNAPSHOT.war
EXPOSE 9090
CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=9090 -jar praticandoAPI-0.0.1-SNAPSHOT.war