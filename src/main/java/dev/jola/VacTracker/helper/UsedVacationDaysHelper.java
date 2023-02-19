package dev.jola.VacTracker.helper;


import dev.jola.VacTracker.entity.UsedVacationDays;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class UsedVacationDaysHelper {

    static String[] HEADERs={"Employee","Vacation start date","Vacation end date"};

    public static List<UsedVacationDays> csvToUsedVacationDays(InputStream inputStream) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<UsedVacationDays> usedVacationDays = new ArrayList<UsedVacationDays>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                UsedVacationDays record = new UsedVacationDays();

                record.setEmployeeEmail(csvRecord.get("Employee"));
                record.setStartDate(csvRecord.get("Vacation start date"));
                record.setEndDate(csvRecord.get("Vacation end date"));


                usedVacationDays.add(record);
            }

            return usedVacationDays;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }





    }
}
