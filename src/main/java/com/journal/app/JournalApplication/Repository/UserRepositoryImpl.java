package com.journal.app.JournalApplication.Repository;

import com.journal.app.JournalApplication.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl{
    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getAllUsersForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));
        List<User> userList = mongoTemplate.find(query, User.class);
        return userList;
    }
}
