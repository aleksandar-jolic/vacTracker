package dev.jola.VacTracker.service;

import dev.jola.VacTracker.dto.VacationDaysPeriodDto;
import dev.jola.VacTracker.entity.Employee;
import dev.jola.VacTracker.entity.UsedVacationDays;
import dev.jola.VacTracker.exception.EmployeeNotFoundException;
import dev.jola.VacTracker.helper.UsedVacationDaysHelper;
import dev.jola.VacTracker.repository.EmployeeRepository;
import dev.jola.VacTracker.repository.UsedVacationDaysRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UsedVacationDaysService {


    private final UsedVacationDaysRepository usedVacationDaysRepository;

    private final EmployeeRepository employeeRepository;

    private final MongoTemplate mongoTemplate;

    public UsedVacationDaysService(UsedVacationDaysRepository usedVacationDaysRepository, MongoTemplate mongoTemplate,EmployeeRepository employeeRepository) {
        this.usedVacationDaysRepository = usedVacationDaysRepository;
        this.mongoTemplate = mongoTemplate;
        this.employeeRepository = employeeRepository;
    }

    public void saveFromFile(MultipartFile file) {

        try{

            List<UsedVacationDays> days = UsedVacationDaysHelper.csvToUsedVacationDays(file.getInputStream());

            for (UsedVacationDays record : days){

                usedVacationDaysRepository.insert(record);

                mongoTemplate.update(Employee.class).matching(Criteria.where("email").is(record.getEmployeeEmail()))
                        .apply(new Update().push("usedVacationDaysIds").value(record)).first();


            }

        }catch (Exception e){

            throw new RuntimeException("Fail to store csv data: " + e.getMessage());

        }
    }


    public List<VacationDaysPeriodDto> getListOfUsedVacationDays(String email, VacationDaysPeriodDto period) throws EmployeeNotFoundException {


        if(!employeeRepository.existsByEmail(email)){

            throw new EmployeeNotFoundException("Employee record does not exist.");

        }

        List<VacationDaysPeriodDto> finalList = new ArrayList<VacationDaysPeriodDto>();

        List<UsedVacationDays> list = usedVacationDaysRepository.findUsedVacationDaysByEmployeeEmail(email);

        DateFormat formatter = new SimpleDateFormat("EEE, MMMM dd, yyyy");

        // define the variables for the start and end date from the database

        Date dbStartDate = null;
        Date dbEndDate = null;

        try {

           // convert the entered date values into Data objects

            Date enteredStartDate =  formatter.parse(period.getStartDate());
            Date enteredEndDate =  formatter.parse(period.getEndDate());

            //define the variables for a result that will be setted in return value - VacationDaysPeriodDto

            String resultStartDate = null;
            String resultEndDate = null;



            for (UsedVacationDays record : list) {

                //convert a string values for the start and end dates from database, from String to Date

                dbStartDate = formatter.parse(record.getStartDate());
                dbEndDate = formatter.parse(record.getEndDate());

                if(!(enteredEndDate.before(dbStartDate) || enteredStartDate.after(dbEndDate))){

                    if(enteredStartDate.before(dbStartDate)){

                        resultStartDate = formatter.format(dbStartDate);

                        if(enteredEndDate.after(dbEndDate)){
                            resultEndDate = formatter.format(dbEndDate);
                        }else{

                            resultEndDate = formatter.format(enteredEndDate);
                        }




                    }else if((enteredStartDate.after(dbStartDate) || enteredStartDate.equals(dbStartDate)) &&
                            (enteredEndDate.before(dbEndDate) || enteredEndDate.equals(dbEndDate))){

                        resultStartDate = formatter.format(enteredStartDate);
                        resultEndDate = formatter.format(enteredEndDate);

                    }
                    else{

                        resultStartDate = formatter.format(enteredStartDate);
                        resultEndDate = formatter.format(dbEndDate);

                    }
                    finalList.add(new VacationDaysPeriodDto(email,resultStartDate,resultEndDate));

                }


            }

            } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        return finalList;

    }
}
