package com.journalappdemo.repository;

import com.journalappdemo.entity.JournalEntry;
import com.journalappdemo.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    void deleteByUserName(String name);
}
