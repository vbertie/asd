FROM openjdk:8
ADD target/user-service.jar user-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=container", "user-service.jar"]