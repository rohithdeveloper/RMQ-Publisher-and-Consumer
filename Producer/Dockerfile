FROM openjdk:17.0.2
VOLUME /tmp
EXPOSE 8080
ADD target/Producer-0.0.1-SNAPSHOT.jar Producer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Producer-0.0.1-SNAPSHOT.jar"]