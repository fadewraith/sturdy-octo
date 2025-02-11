package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Employee;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

//        demo of providing dynamic name to custom persistence unit as in first lecture, it was hard coded
        String persistenceUnitName = "persistence-unit-name";
        Map<?, ?> props = new HashMap<>();

        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                new CustomPersistenceUnitInfo(persistenceUnitName),
                props
        ); // second param can be used to provide properties, but leaving empty here
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();

//            if we write same multiple queries, there will be one query only and after one instance it will be out of the context
//            Employee e1 = em.find(Employee.class, 1);
//            e1.setName("John"); // this one is irrelevant
////            e1.setName("Oliver Drake");
//
//            em.remove(e1); // remove instance from the context, not from the db

//            creating the instance like this, creates it outside the context, thats why this data will not be saved in db, coz we are already having data with same p.k. in db
            Employee e1 = new Employee();
            e1.setId(1);
            e1.setName("John Doe");
            e1.setAddress("Dummy address");
            em.merge(e1); // now the above will work

            /**
             * all the operations that can be done with the entity
             * em.persist() -> adding an entity in the context
             * em.find()    -> finds by pk, get from db and add it to the context if it doesn't already exist
             * em.remove()  -> marking entity for removal
             * em.merge()   -> merges an entity from outside the context to the context
             * em.refresh() -> mirror the context from the db
             * em.detach()  -> taking the entity out of the context
             * em.getReference()
             */

//            Employee e2 = new Employee();
//            e2.setId(1);
//            e2.setName("John Doe");
//            e2.setAddress("123 Main St");
//            em.persist(e2);
//
//            System.out.println(e1);

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }

    }
}