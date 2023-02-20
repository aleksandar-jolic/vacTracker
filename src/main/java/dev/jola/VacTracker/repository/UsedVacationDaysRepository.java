package dev.jola.VacTracker.repository;

import dev.jola.VacTracker.entity.UsedVacationDays;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedVacationDaysRepository extends MongoRepository<UsedVacationDays, ObjectId> {


    public List<UsedVacationDays> findUsedVacationDaysByEmployeeEmail(String email);






}
