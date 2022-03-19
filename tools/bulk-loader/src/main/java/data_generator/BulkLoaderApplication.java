package data_generator;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionStage;

@SpringBootApplication
public class BulkLoaderApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BulkLoaderApplication.class);
    @Autowired
    private CqlSession session;
    @Value("${bulk_loader.batch_size}")
    private Integer batchSize;
    @Value("${bulk_loader.batches_to_insert}")
    private Integer batchesToInsert;
    @Value("${bulk_loader.n_threads}")
    private Integer nThreads;
    @Value("${bulk_loader.id_file}")
    private String idFile;
    @Value("${bulk_loader.n_users}")
    private Integer nUsers;

    private Long[] ids;

    private Random random = new Random();

    public static void main(String[] args) {
        var app = new SpringApplication(BulkLoaderApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    private void batchInsert(int nRows) {
        var preparedStatement = session.prepare("insert into kion.user_event (user_id, video_id, event_type, event_time) values(?,?,?,?)");
        var jobs = new ArrayList<CompletionStage<?>>();
        for (int i = 0; i < nRows; ++i) {
            jobs.add(session.executeAsync(preparedStatement.bind(
                    (long) ids[random.nextInt(ids.length)],
                    (long) random.nextInt(1000000) + 1,
                    (long) random.nextInt(10) + 1,
                    (long) random.nextInt(Integer.MAX_VALUE)
            )));

            if (jobs.size() >= nThreads) {
                for (var future : jobs) {
                    try {
                        future.toCompletableFuture().join();
                    } catch (Exception ignore) {
                        log.warn("async insert was interrupted");
                    }
                }
                jobs.clear();
            }
        }
    }

    private void inflateUserEventTable() {
        for (int i = 0; i < batchesToInsert; ++i) {
            long start = System.currentTimeMillis();
            batchInsert(batchSize);
            long end = System.currentTimeMillis();
            System.out.println("written " + ((i + 1) * batchSize) + " rows " + " (" + (end - start) + " ms)");
        }
    }

    private Long[] readIds(String path) throws Exception {
        try (var in = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            var ids = new ArrayList<Long>();
            String line;
            while ((line = in.readLine()) != null) {
                ids.add(Long.parseLong(line));
            }
            return ids.toArray(new Long[0]);
        }
    }

    @Override public void run(String... args) throws Exception {
        try {
            this.ids = readIds(idFile);
            inflateUserEventTable();
        } finally {
            session.close();
        }
    }
}
