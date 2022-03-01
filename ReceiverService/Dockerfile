FROM bellsoft/liberica-openjdk-alpine:11.0.5

ENV APP_FILE receiver-service-0.0.1.jar
ENV APP_HOME /app

EXPOSE 8080

COPY target/$APP_FILE $APP_HOME/

WORKDIR $APP_HOME

ENTRYPOINT ["sh", "-c"]

CMD ["exec java -jar $JAVA_OPTS $APP_FILE"]