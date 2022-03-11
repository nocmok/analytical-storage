package dummy_receiver;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;

@Configuration
public class JettyConfig {

    private Connector getHttpConnector(Server server) {
        HttpConfiguration httpConfig = new HttpConfiguration();
        HttpConnectionFactory http11 = new HttpConnectionFactory(httpConfig);
        ServerConnector connector = new ServerConnector(server, http11);
        connector.setHost("192.168.88.129");
        connector.setPort(8080);
        return connector;
    }

    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory() {
        var factory = new JettyServletWebServerFactory();
        factory.setPort(8080);
        factory.setAcceptors(1);
        factory.addServerCustomizers(
                (server -> {
//                    QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
//                    threadPool.setMinThreads(8);
//                    threadPool.setMaxThreads(100);
                })
        );
        factory.setSelectors(2);
        factory.setThreadPool(new QueuedThreadPool(1000,8));
        return factory;
    }
}
