package org.example;

import jakarta.persistence.*;
import org.example.dto.CountedEnrollmentForStudent;
import org.example.dto.EnrolledStudent;
import org.example.entities.DistinctStudent;
import org.example.entities.Student;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.*;

public class Main {

//    foreign key will only be to the side where many is present
    public static void main(String[] args) {
//        composed primary key
        String persistenceUnitName = "pu-name";

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
         props.put("hibernate.hbm2ddl.auto", "none"); // create, none, update
        //        <property name="hibernate.format_sql" value="true"/>

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(
                        new CustomPersistenceUnitInfo(persistenceUnitName),
                        props
                );

        EntityManager em = emf.createEntityManager(); // never use hibernate directly

        try {
            em.getTransaction().begin();

//            SELECT s FROM Student s --> JPQL
//            String sql = """
//                    SELECT * FROM Student
//                    """;
//            Query q = em.createNativeQuery(sql, Student.class);
//
//            q.getResultList().forEach(s -> System.out.println(s));

//            String sql = "SELECT s FROM DistinctStudent s";
//
//            TypedQuery<DistinctStudent> q = em.createQuery(sql, DistinctStudent.class);
//            q.getResultList().forEach(s -> System.out.println(s));

//            delimiter //
//            create procedure GetStudents(in id int)
//            begin
//            select * from student s where s.id = id;
//            end //
//                    delimiter ;
//            call GetStudents(1);

            StoredProcedureQuery q = em.createStoredProcedureQuery("GetStudents", Student.class)
                    .registerStoredProcedureParameter("id", Integer.class, ParameterMode.IN)
                    .setParameter("id", 2);
            q.getResultList().forEach(o -> System.out.println(o));


            em.getTransaction().commit(); // end of transaction is when the context is mirrored
        } finally {
            em.close();
        }
    }

}

//for many to  many, we can create join table and that table will not be an entity


//            em.persist();   -> Adding an entity in the context
//            em.find()       -> Finds by PK. Get from DB and add it to the context if it doesn't already exist
//            em.remove();    -> Marking entity for removal
//            em.merge();     -> Merges an entity from outside the context to the context.
//            em.refresh();   -> Mirror the context from the database
//            em.detach();    -> Taking the entity out of the context
//            em.getReference() -> kinda lazy type, will execute the query only if used