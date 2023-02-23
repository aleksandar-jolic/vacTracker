package dev.jola.VacTracker.repository;

import dev.jola.VacTracker.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    public boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
