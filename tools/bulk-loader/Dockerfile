FROM bellsoft/liberica-openjdk-alpine:11.0.5

COPY ./build/libs/bulk_loader-0.0.1.jar /opt/bulk_loader/bulk_loader-0.0.1.jar

WORKDIR /opt/bulk_loader/

CMD java -jar $JAVA_OPTS bulk_loader-0.0.1.jar $JAVA_ARGS