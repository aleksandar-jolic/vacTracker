package dev.jola.VacTracker.service;

import dev.jola.VacTracker.entity.Employee;
import dev.jola.VacTracker.entity.VacationPerYear;
import dev.jola.VacTracker.helper.VacationPerYearHelper;
import dev.jola.VacTracker.repository.VacationPerYearRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class VacationPerYearService {


    private final VacationPerYearRepository vacationPerYearRepository;
    private final MongoTemplate mongoTemplate;
    private final VacationPerYearHelper vacationPerYearHelper;

    public VacationPerYearService(VacationPerYearRepository vacationPerYearRepository,
                                  MongoTemplate mongoTemplate,
                                  VacationPerYearHelper vacationPerYearHelper) {
        this.vacationPerYearRepository = vacationPerYearRepository;
        this.mongoTemplate = mongoTemplate;
        this.vacationPerYearHelper = vacationPerYearHelper;
    }

    public void saveFromFile(MultipartFile file) {

        try{

            List<VacationPerYear> days = vacationPerYearHelper.csvToVacationPerYear(file.getInputStream());

            for (VacationPerYear record : days){

                vacationPerYearRepository.insert(record);

                mongoTemplate.update(Employee.class).matching(Criteria.where("email").is(record.getEmployeeEmail()))
                        .apply(new Update().push("vacationPerYearIds").value(record)).first();


            }




        }catch (Exception e){

            throw new RuntimeException("Fail to store csv data: " + e.getMessage());

        }
    }


}
