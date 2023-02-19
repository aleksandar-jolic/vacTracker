package dev.jola.VacTracker.helper;


import dev.jola.VacTracker.entity.Employee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class EmployeeHelper {

    public static List<Employee> csvToEmployees(InputStream inputStream) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader("Employee Email","Employee Password").withIgnoreHeaderCase().withTrim());) {

            List<Employee> employees = new ArrayList<Employee>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            // we set the value of the counter to 0, so that the first two rows are not loaded into the list

            int counter = 0;
            String year = "";
            for (CSVRecord csvRecord : csvRecords) {

                if(counter == 0){

                    year= csvRecord.get(1);

                    System.out.println(year);

                }else if(counter == 1){

                    System.out.println("Headers: "+ csvRecord.get("Employee Email")+","+csvRecord.get("Employee Password"));

                }
                else{
                    Employee record = new Employee();

                    record.setEmail(csvRecord.get("Employee Email"));
                    record.setPassword(csvRecord.get("Employee Password"));
                    record.setVacationPerYearIds(new ArrayList<>());
                    record.setUsedVacationDaysIds(new ArrayList<>());
                    System.out.println(record);
                    System.out.println("///////////////////////////");
                    System.out.println("///////////////////////");



                    employees.add(record);


                }
                counter++;
            }

            return employees;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }





    }





}
