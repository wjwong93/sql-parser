package com.wjwong93.polystore;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.iq80.leveldb.*;
import org.fusesource.leveldbjni.JniDBFactory;
import java.io.*;
import java.util.List;

public class LevelDBSetup {
    static File dbPath = new File("./leveldb");
    static Options options = new Options();

    public static void main(String[] args) {
        try {
            deleteDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String[]> csvData = loadDataFromCSV("yasuda_data/data_for_leveldb_1.csv");

        options.createIfMissing(true);
        try (
            DB db = JniDBFactory.factory.open(dbPath, options);
            WriteBatch batch = db.createWriteBatch()
        ) {
            for (String[] row : csvData) {
                String key = row[0] + "_" + row[2];
                String value = row[1];
                System.out.println(key + ", " + value);

                batch.put(JniDBFactory.bytes(key), JniDBFactory.bytes(value));
            }

            db.write(batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String[]> loadDataFromCSV(String filepath) {
        List<String[]> result = null;
        try {
            FileReader fileReader = new FileReader(filepath);
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1)
                    .build();
            result = csvReader.readAll();
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    static void deleteDB() throws IOException {
        JniDBFactory.factory.destroy(dbPath, options);
    }


}
