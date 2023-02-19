package dev.jola.VacTracker.repository;

import dev.jola.VacTracker.entity.VacationPerYear;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationPerYearRepository extends MongoRepository<VacationPerYear, ObjectId> {
}
