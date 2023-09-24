FROM openjdk:17
COPY / /
RUN ["./gradlew", "assemble", "classes", "jar"]
ENTRYPOINT ["java", "-cp", "build/libs/Game-1.0-SNAPSHOT.jar", "org.example.Main"]
