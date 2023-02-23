package dev.jola.VacTracker.repository;

import dev.jola.VacTracker.entity.VacationPerYear;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacationPerYearRepository extends MongoRepository<VacationPerYear, ObjectId> {


    @Query("{employeeEmail: ?0, year: ?1}")
    public Optional<VacationPerYear> findVacationPerYearByEmployeeEmailAndYear(String email, int year);



}
