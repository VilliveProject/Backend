FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./Villive_Backend.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./Villive_Backend.jar"]