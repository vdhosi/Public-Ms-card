FROM gradle:jdk11 as build
COPY --chown=gradle:gradle . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build
RUN gradle sonarqube 
RUN gradle publish 


FROM openjdk:11-jre-slim
COPY --from=build /home/gradle/source/build/libs/app-ms-card-#{version}#.jar /app/
WORKDIR /app
ENTRYPOINT [ "sh", "-c", "java -jar app-ms-card-#{version}#.jar"]