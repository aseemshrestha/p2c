FROM openjdk:8
LABEL maintainer="shrestha.asm@gmail.com"
ADD target/p2c.jar p2c.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "p2c.jar"]
