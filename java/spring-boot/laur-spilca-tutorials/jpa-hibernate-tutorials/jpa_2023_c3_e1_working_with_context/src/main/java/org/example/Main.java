package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Employee;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String persistenceUnitName = "pu-name";

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create"); // create, none, update
        //        <property name="hibernate.format_sql" value="true"/>
//        ddl -> data definition language
//        if we have more than 1 entity, we have to create table ourselves, in prod we use none

//        other possible soln -
//        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
//        query.setParameter("id", 1);
//        Employee e1 = query.getSingleResult();
//        System.out.println("e1 -> " + e1);

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(persistenceUnitName), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            var e1 = new Employee();
            e1.setAddress("Address");
            e1.setName("John");
//
            em.persist(e1); // persist might happen before or after
//
//            e1 = em.find(Employee.class, 1);
//            System.out.println("e1 -> " + e1);

//            getReference demo - it is basically a decorator pattern, like lazily loaded
            // if found, entity will be added to the context & query will be sent to db
//            var e1 = em.find(Employee.class, 1);
//            no query will be sent to db
//            var e1 = em.getReference(Employee.class, 1); // if found, entity will be added to the context
////            query will be run, if we print on console or do anything
//            System.out.println(e1);
//            // e1.setName("Anna");
//            e1.setName("Hello"); // suppose by mistake done any changes

            // its undo in the context
//            em.refresh(e1);

//            System.out.println(e1);

//            em.persist();   -> Adding an entity in the context
//            em.find()       -> Finds by PK. Get from DB and add it to the context if it doesn't already exist
//            em.remove();    -> Marking entity for removal
//            em.merge();     -> Merges an entity from outside the context to the context.
//            em.refresh();   -> Mirror the context from the database
//            em.detach();    -> Taking the entity out of the context
//            em.getReference()

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }

}