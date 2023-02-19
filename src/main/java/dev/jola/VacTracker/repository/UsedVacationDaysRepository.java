package dev.jola.VacTracker.repository;

import dev.jola.VacTracker.entity.UsedVacationDays;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedVacationDaysRepository extends MongoRepository<UsedVacationDays, ObjectId> {
}
