package com.springbootlearning.cruddemo.dao;


import com.springbootlearning.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

//    define field for entity manager
    private EntityManager entityManager;

//    inject entity manager using constructor injection

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


//    implement save method
    @Override
    @Transactional
    public void save(Student theStudent) {
        entityManager.persist(theStudent);
    }

    @Override
    public Student findById(Integer id) {
//        where Student is the entity class and id is the primary key
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {

//        create query
//        where Student is the entity name, i.e. class name and its not the name of the db table, where the lastNAme is the field name of the Studnt class
        TypedQuery<Student> theQuery = entityManager.createQuery("From Student order by lastName desc", Student.class);

//        return query results
        return theQuery.getResultList();
    }

    @Override
    public List<Student> findByLastName(String theLastName) {

//        create query, where :theData is a placeholder which will be filled later
        TypedQuery<Student> theQuery = entityManager.createQuery("From Student where lastName=:theData", Student.class);

//        set query parameters, where here theData is the parameter name and it will be replaced above by theLastName, which we are passing in the method above
        theQuery.setParameter("theData", theLastName);

//        return query results
        return theQuery.getResultList();
    }

    @Override
    @Transactional // mandatory, because we're performing the update query
    public void update(Student theStudent) {
        entityManager.merge(theStudent);
    }

    @Override
    @Transactional // necessary, because we're modifying the database
    public void delete(Integer id) {

//        retreive the student
        Student theStudent = entityManager.find(Student.class, id);

//        delete the student
        entityManager.remove(theStudent);
    }

    @Override
    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("delete from Student").executeUpdate();
    }
}
