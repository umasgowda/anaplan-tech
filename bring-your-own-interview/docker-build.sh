#tag the image as usg/dashboards
docker build --build-arg JAR_FILE=target/bring-your-own-interview*.jar -t usg/dashboards .
