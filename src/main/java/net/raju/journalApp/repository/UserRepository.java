package net.raju.journalApp.repository;

import net.raju.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
     User findByUserName(String username);

     void deleteByUserName(String username);
}