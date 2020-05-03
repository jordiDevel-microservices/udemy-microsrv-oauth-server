FROM openjdk:8-slim
VOLUME /tmp
COPY ./target/springboot-servicio-oauth-server-0.0.1-SNAPSHOT.jar oauth-server.jar
COPY ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ./wait-for-it.sh -t 0 eureka-server:8761 -- java -jar -Dspring.profiles.active=docker oauth-server.jar
