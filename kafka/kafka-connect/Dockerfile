FROM confluentinc/cp-kafka-connect:latest

USER root
RUN yes y | yum install unzip
RUN mkdir -p /data/connect-jars/ \ 
	&& curl -o /data/connect-jars/cassandra-sink.zip https://d1i4a15mxbxib1.cloudfront.net/api/plugins/confluentinc/kafka-connect-cassandra/versions/2.0.0/confluentinc-kafka-connect-cassandra-2.0.0.zip \
	&& unzip /data/connect-jars/cassandra-sink.zip -d /data/connect-jars/ \
	&& mv /data/connect-jars/confluentinc-kafka-connect-cassandra-2.0.0 /data/connect-jars/cassandra-sink \
	&& rm /data/connect-jars/cassandra-sink.zip
RUN mkdir -p /opt/prometheus \
	&& curl -o /opt/prometheus/jmx_prometheus_javaagent-0.16.1.jar https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.16.1/jmx_prometheus_javaagent-0.16.1.jar