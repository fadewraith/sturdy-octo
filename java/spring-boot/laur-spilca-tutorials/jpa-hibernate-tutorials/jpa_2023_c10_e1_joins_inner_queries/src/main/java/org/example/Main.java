package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.dto.CountedEnrollmentForStudent;
import org.example.dto.EnrolledStudent;
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

//            String jpql = """
//                    SELECT s, e FROM Student s INNER JOIN s.enrollments e
//                    """;

//            String jpql = """
//                    SELECT s, e FROM Student s JOIN s.enrollments e
//                    """;

//            String jpql = """
//                    SELECT s, e FROM Student s, Enrollment e WHERE s.id = e.student.id
//                    """;

//            String jpql = """
//                    SELECT s, e FROM Student s, Enrollment e WHERE s = e.student
//                    """;

//            String jpql = """
//                   SELECT s, e FROM Student s LEFT JOIN s.enrollments e
//                    """;

//            String jpql = """
//                   SELECT s, e FROM Student s RIGHT JOIN s.enrollments e
//                    """;

//            TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
//            //            coz we have used Objects[] and below getting result in list
////            o/p (list of arrays) = [s1, e1], [s2, e2], ...
//
//            q.getResultList().forEach(o -> System.out.println("Stu: " + o[0] + ", Enroll:  " + o[1]));

//           records dto demo, works with jpql not with native queries
//            String jpql = """
//                   SELECT NEW org.example.dto.EnrolledStudent(s, e) FROM Student s RIGHT JOIN s.enrollments e
//                    """;

//            TypedQuery<EnrolledStudent> q = em.createQuery(jpql, EnrolledStudent.class);
//            q.getResultList().forEach(o -> System.out.println("Stu: " + o.student() + ", Enroll:  " + o.enrollment()));

//            String jpql = """
//                    SELECT s FROM Student s WHERE
//                        (SELECT COUNT(e) FROM Enrollment e WHERE e.student.id = s.id) > 1
//                    """;

            String jpql = """
                    SELECT NEW org.example.dto.CountedEnrollmentForStudent(s, (SELECT count(e) FROM Enrollment e WHERE e.student = s) )
                    FROM Student s 
                    """;


            TypedQuery<CountedEnrollmentForStudent> q = em.createQuery(jpql, CountedEnrollmentForStudent.class);
            q.getResultList().forEach(o -> System.out.println("Stu: " + o.s() + ", Enroll:  " + o.count()));


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