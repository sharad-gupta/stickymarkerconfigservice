FROM openjdk:8-jdk-alpine
ADD sticky-marker-service-0.0.1-SNAPSHOT.jar /stickymarker.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java -jar /stickymarker.jar"]