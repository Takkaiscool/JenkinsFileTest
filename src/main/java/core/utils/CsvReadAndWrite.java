package core.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/***
 * @author Sai Ram Prasath
 */
public class CsvReadAndWrite {
    private static String fileName;
    private CSVParser csvParser;

    public CsvReadAndWrite(String fileName) {
        System.out.println(fileName);
        System.out.println(fileName.endsWith(".csv"));
        if (fileName.endsWith(".csv")) {
            try {
                File file = new File(fileName);
                Reader reader = new FileReader(file);
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public List<LinkedHashMap<String, String>> readData() {
        List<LinkedHashMap<String, String>> data = new ArrayList<>();
        try {
            List<CSVRecord> records = csvParser.getRecords();
            for (int i = 1; i < records.size(); i++) {
                LinkedHashMap<String, String> rowData = new LinkedHashMap<>();
                CSVRecord record = records.get(i);
                for (int j = 0; j < record.size(); j++) {
                    rowData.put(records.get(0).get(j).trim(), record.get(j).trim());
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}

