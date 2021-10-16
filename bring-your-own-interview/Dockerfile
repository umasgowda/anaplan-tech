FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/bring-your-own-interview*.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:1000
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]