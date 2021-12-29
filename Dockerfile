FROM openjdk:11
MAINTAINER Mahendra
COPY target/api-0.0.1-SNAPSHOT.jar /opt/api.jar
RUN chmod +x /opt/api.jar
EXPOSE 8081
WORKDIR /opt/
ENTRYPOINT ["java", "-jar", "/opt/api.jar"]