FROM wurstmeister/kafka

ADD prom-jmx-agent-config.yml /usr/app/prom-jmx-agent-config.yml
ADD create-topics.sh /usr/bin/create-topics.sh
ADD https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.16.1/jmx_prometheus_javaagent-0.16.1.jar /usr/app/jmx_prometheus_javaagent.jar