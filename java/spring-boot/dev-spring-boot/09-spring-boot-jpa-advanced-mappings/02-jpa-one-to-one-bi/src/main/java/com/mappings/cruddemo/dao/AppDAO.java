package com.mappings.cruddemo.dao;

import com.mappings.cruddemo.entity.Instructor;
import com.mappings.cruddemo.entity.InstructorDetail;

public interface AppDAO {

    void save(Instructor instructor);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail findInstructorDetailById(int id);

    void deleteInstructorDetailById(int id);
}
