FROM bellsoft/liberica-openjdk-alpine:11.0.5

COPY ./build/libs/http_adapter-0.0.1.jar /opt/http_adapter/http_adapter-0.0.1.jar

WORKDIR /opt/http_adapter/

CMD java -jar $JAVA_OPTS http_adapter-0.0.1.jar