package uid_partitioner;

import com.datastax.oss.driver.internal.core.metadata.token.Murmur3TokenFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Принимает выхлоп утилиты nodetool ring.
 * Генерирует набор файлов.
 * Каждый файл состоит из наборов partition key, за которые отвечает одна нода.
 */
public class HostToUidsMapper {

    private Murmur3TokenFactory partitioner = new Murmur3TokenFactory();
    private ByteBuffer buffer = ByteBuffer.allocate(8);

    private BigInteger[] tokens;
    private String[] hosts;

    public HostToUidsMapper(String ringFile) throws Exception {
        this.tokens = parseTokens(ringFile);
        this.hosts = parseHosts(ringFile);
    }

    public static void main(String[] args) throws Exception {
        var app = new HostToUidsMapper("ring.txt");

        for (long i = 1; i <= 100; ++i) {
            System.out.println(app.getHostByUid(i));
        }

//        var uids = LongStream.range(1, 1_000_001).boxed().collect(Collectors.toList());
//        var hostToUidsMapping = app.getUidsPerHosts(uids);

//        for (var hostToUids : hostToUidsMapping.entrySet()) {
//            try (var out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(hostToUids.getKey() + ".txt")))) {
//                for (Long uid : hostToUids.getValue()) {
//                    out.println(uid);
//                }
//            }
//        }
    }

    private BigInteger getToken(long uid) {
        buffer.putLong(0, uid);
        String token = partitioner.hash(buffer).toString();
        return new BigInteger(token.substring("Murmur3Token(".length(), token.length() - 1));
    }

    private BigInteger[] parseTokens(String ringFile) throws Exception {
        List<BigInteger> tokens = new ArrayList<>();
        var rawIn = HostToUidsMapper.class.getClassLoader().getResourceAsStream(ringFile);
        if (rawIn == null) {
            throw new RuntimeException("no such file " + ringFile);
        }
        try (var in = new BufferedReader(new InputStreamReader(rawIn))) {
            String line;
            while ((line = in.readLine()) != null) {
                tokens.add(new BigInteger(line.split("\\s+")[1]));
            }
            return tokens.toArray(new BigInteger[0]);
        }
    }

    private String[] parseHosts(String ringFile) throws Exception {
        List<String> tokens = new ArrayList<>();
        var rawIn = HostToUidsMapper.class.getClassLoader().getResourceAsStream(ringFile);
        if (rawIn == null) {
            throw new RuntimeException("no such file " + ringFile);
        }
        try (var in = new BufferedReader(new InputStreamReader(rawIn))) {
            String line;
            while ((line = in.readLine()) != null) {
                tokens.add(line.split("\\s+")[0]);
            }
            return tokens.toArray(new String[0]);
        }
    }

    private String getHostByToken(BigInteger token) {
        int index = Arrays.binarySearch(tokens, token);
        if (index >= 0) {
            return hosts[index];
        } else {
            return hosts[(-index - 1) % tokens.length];
        }
    }

    private String getHostByUid(Long uid) {
        int index = Arrays.binarySearch(tokens, getToken(uid));
        if (index >= 0) {
            return hosts[index];
        } else {
            return hosts[(-index - 1) % tokens.length];
        }
    }

    public Map<String, List<Long>> getUidsPerHosts(List<Long> uids) {
        var mapping = new HashMap<String, List<Long>>();
        for (Long uid : uids) {
            mapping.computeIfAbsent(getHostByUid(uid), (k) -> new ArrayList<>())
                    .add(uid);
        }
        return mapping;
    }
}
