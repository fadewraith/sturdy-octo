package com.mappings.cruddemo.dao;

import com.mappings.cruddemo.entity.Instructor;

public interface AppDAO {

    void save(Instructor instructor);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);
}
