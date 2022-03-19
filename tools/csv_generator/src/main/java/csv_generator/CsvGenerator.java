package csv_generator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class CsvGenerator {

    private String csvPath;
    private Long rowsToGenerate;
    private String[] ids;
    private Random random = new Random();

    public CsvGenerator(String csvPath, String idsPath, Long rowsToGenerate) throws Exception {
        this.csvPath = csvPath;
        this.rowsToGenerate = rowsToGenerate;
        this.ids = readIds(idsPath);
    }

    public static void main(String[] args) throws Exception {
        // Вход:
        // Количество строк которые нужно сгенерить
        // Файл с айдишками пользователей

        // Берем рандомный айдишник из списка
        // Генерим для него рандомную запись

        var csvGenerator = new CsvGenerator("events.csv", "./193.8.211.143.txt", 30000000L);
        csvGenerator.generateCsv();
    }

    private String[] readIds(String path) throws Exception {
        try (var in = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            var ids = new ArrayList<String>();
            String line;
            while ((line = in.readLine()) != null) {
                ids.add(line);
            }
            return ids.toArray(new String[0]);
        }
    }

    public void generateCsv() throws Exception {
        try (var out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(csvPath)))) {
            out.println("user_id,video_id,event_type,event_time");
            for (int i = 0; i < rowsToGenerate; ++i) {
                out.println(
                        ids[random.nextInt(ids.length)] + "," + random.nextInt(1000001) + 1 + "," + random.nextInt(11) + 1 + "," + random.nextInt(100000000));
            }
        }
    }
}
