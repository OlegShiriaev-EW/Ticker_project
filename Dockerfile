FROM openjdk:11
COPY target/*.jar ticker_app.jar
ENTRYPOINT ["java", "-jar", "ticker_app.jar"]