package dev.jola.VacTracker.helper;


import dev.jola.VacTracker.entity.VacationPerYear;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class VacationPerYearHelper {

    public  List<VacationPerYear> csvToVacationPerYear(InputStream inputStream) {


        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader("Employee","Total vacation days").withIgnoreHeaderCase().withTrim());) {

            List<VacationPerYear> days = new ArrayList<VacationPerYear>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();


            // we set the value of the counter to 0, so that the first two rows are not loaded into the list


            int counter = 0;
            String year = "";
            for (CSVRecord csvRecord : csvRecords) {

                if(counter == 0){

                    year= csvRecord.get(1);

                    System.out.println(year);

                }else if(counter == 1){

                    System.out.println("Headers: "+ csvRecord.get("Employee")+","+csvRecord.get("Total vacation days"));

                }
                else{
                    VacationPerYear record = new VacationPerYear();

                    record.setEmployeeEmail(csvRecord.get("Employee"));
                    record.setYear(Integer.parseInt(year));
                    record.setTotalVacationDays(Integer.parseInt(csvRecord.get("Total vacation days")));





                    days.add(record);


                }
                counter++;
            }

            return days;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }






    }


}
