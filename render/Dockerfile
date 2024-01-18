FROM openjdk:21
VOLUME /tmp
COPY target/render-0.0.1-SNAPSHOT.jar render.jar
ENTRYPOINT ["java","-jar","/render.jar"]