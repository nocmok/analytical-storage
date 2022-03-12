FROM cassandra:3.11

RUN mkdir -p /opt/prometheus \
	&& curl -o /opt/prometheus/jmx_prometheus_javaagent-0.16.1.jar https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.16.1/jmx_prometheus_javaagent-0.16.1.jar
ADD prom-jmx-agent-config.yml /opt/prometheus/prom-jmx-agent-config.yml