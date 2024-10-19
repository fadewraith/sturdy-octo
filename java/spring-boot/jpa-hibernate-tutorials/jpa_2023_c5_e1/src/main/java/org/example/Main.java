package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.entities.Passport;
import org.example.entities.Person;
import org.example.entities.User;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        composed primary key
        String persistenceUnitName = "pu-name";

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create"); // create, none, update
        //        <property name="hibernate.format_sql" value="true"/>


        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(persistenceUnitName), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Person person = new Person();
            person.setName("John");

            Passport passport = new Passport();
            passport.setNumber("ABC123");

            person.setPassport(passport);
            passport.setPerson(person); // in case of bidirectional

//            here we have to persist both, order doesn't matter in persist
            em.persist(person);
//            instead of using persist, we can use CASCADE
//            em.persist(passport);

//            TypedQuery<Person> personTypedQuery = em.createQuery("SELECT p FROM Person p WHERE p.passport.number = :number", Person.class);
//            personTypedQuery.setParameter("number", "ABC123");
//            System.out.println(personTypedQuery.getResultList());

            User user = new User();
            user.setName("Doe");
            user.setDescription("doe description");
            em.persist(user);

            em.getTransaction().commit(); // end of transaction is when the context is mirrored
        } finally {
            em.close();
        }
    }

}


//            em.persist();   -> Adding an entity in the context
//            em.find()       -> Finds by PK. Get from DB and add it to the context if it doesn't already exist
//            em.remove();    -> Marking entity for removal
//            em.merge();     -> Merges an entity from outside the context to the context.
//            em.refresh();   -> Mirror the context from the database
//            em.detach();    -> Taking the entity out of the context
//            em.getReference()