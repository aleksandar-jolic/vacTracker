package dev.jola.VacTracker.repository;

import dev.jola.VacTracker.entity.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {

    public boolean existsByEmail(String email);

}
