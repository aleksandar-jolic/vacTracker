package dev.jola.VacTracker.service;

import dev.jola.VacTracker.entity.Employee;
import dev.jola.VacTracker.entity.UsedVacationDays;
import dev.jola.VacTracker.helper.UsedVacationDaysHelper;
import dev.jola.VacTracker.repository.UsedVacationDaysRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UsedVacationDaysService {


    private final UsedVacationDaysRepository usedVacationDaysRepository;


    private final MongoTemplate mongoTemplate;

    public UsedVacationDaysService(UsedVacationDaysRepository usedVacationDaysRepository, MongoTemplate mongoTemplate) {
        this.usedVacationDaysRepository = usedVacationDaysRepository;
        this.mongoTemplate = mongoTemplate;
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




}
