package org.example;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.entities.Customer;
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
//            criteria query cannot be used for inserting, updating & deleting, only for fetching
//            jpql for updates & deletes

            CriteriaBuilder cb = em.getCriteriaBuilder();
//            ex 1
//            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

//            Root<Customer> customerRoot = cq.from(Customer.class);
//            cq.select(customerRoot); // SELECT c FROM Customer c

//            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
//            TypedQuery<Customer> query = em.createQuery(cq);
//=============================================================
////            ex 2
//            CriteriaQuery<String> cq = cb.createQuery(String.class);
//            Root<Customer> customerRoot = cq.from(Customer.class);
//            cq.select(customerRoot.get("name")); // SELECT c.name FROM Customer c
//            TypedQuery<String> query = em.createQuery(cq);
//
//
//            query.getResultList().forEach(System.out::println);
//=============================================================
//            ex 3
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
//            if we use cq.from(Customer.class) multiple times, it will create a new builder each time it is used
            Root<Customer> customerRoot = cq.from(Customer.class);
//            cq.multiselect(customerRoot.get("name"), customerRoot.get("id")); // SELECT c.name, c.id FROM Customer c
            cq.multiselect(
                    customerRoot.get("name"), cb.sum(customerRoot.get("id"))
                    )
                    .where(cb.ge(customerRoot.get("id"), 5))
                    .groupBy(customerRoot.get("name"))
//                    .orderBy(cb.desc(customerRoot.get("id"))); // not working, so modfied below bcoz of default rules 'ONLY_FULL_GROUP_BY' , check -> SELECT @@sql_mode;
            .orderBy(cb.desc(cb.sum(customerRoot.get("id"))));
//            if(condition) {
//                cq.where(cb.ge(customerRoot.get("id"), 5))
//            }
            TypedQuery<Object[]> query = em.createQuery(cq);

            query.getResultList().forEach(o -> System.out.println(o[0] + ", " + o[1]));
//=============================================================

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