package data_generator;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
public class ReadTester implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ReadTester.class);
    @Autowired
    private CqlSession session;
    private Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(ReadTester.class, args);
    }

    private void runSingleCoreBenchmark(int nProbes) {
        var preparedStatement = session.prepare("select * from kion.user_event where user_id = ? limit 500");
        long start = System.currentTimeMillis();
        for (int i = 0; i < nProbes; ++i) {
            session.execute(preparedStatement.bind((long) random.nextInt(100001) + 1));
        }
        long end = System.currentTimeMillis();
        System.out.println("avg " + (end - start) / nProbes);
    }

    private void runMulticoreBenchmark(int probesPerThread, int nThreads) {
        var threadPool = Executors.newFixedThreadPool(nThreads);
        long start = System.currentTimeMillis();
        var futures = new ArrayList<Future<?>>();
        for (int i = 0; i < nThreads; ++i) {
            var future = threadPool.submit(() -> {
                var preparedStatement = session.prepare("select * from kion.user_event where user_id = ? limit 200");
                for (int j = 0; j < probesPerThread; ++j) {
                    session.execute(preparedStatement.bind((long) random.nextInt(100001) + 1));
                }
            });
            futures.add(future);
        }

        for(var future : futures) {
            try {
                future.get();
            }catch (Exception ignore) {
                log.warn(ignore.getMessage());
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("rps " + (1000d * probesPerThread * nThreads / (end - start)));
    }

    @Override public void run(String... args) throws Exception {
        try {
//            runSingleCoreBenchmark(100);
            runMulticoreBenchmark(10,300);
        } finally {
            session.close();
        }
    }
}
