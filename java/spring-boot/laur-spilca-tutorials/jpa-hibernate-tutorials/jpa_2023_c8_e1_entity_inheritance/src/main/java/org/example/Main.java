package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Book;
import org.example.entities.ElectronicDevice;
import org.example.entities.Product;
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
        props.put("hibernate.hbm2ddl.auto", "create"); // create, none, update
        //        <property name="hibernate.format_sql" value="true"/>

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(
                        new CustomPersistenceUnitInfo(persistenceUnitName),
                        props
                );

        EntityManager em = emf.createEntityManager(); // never use hibernate directly

        try {
            em.getTransaction().begin();

//            Book book = new Book();
//            book.setId(1L);
//            book.setAuthor("John Doe");
//
////            DTYPE col will be created - discriminator
//
//            ElectronicDevice electronicDevice = new ElectronicDevice();
//            electronicDevice.setId(2L);
//            electronicDevice.setVoltage(220);


//            em.persist(book);
//            em.persist(electronicDevice);

//            var sql = "SELECT p FROM Product p";
//            em.createQuery(sql, Product.class)
//                            .getResultList().forEach(System.out::println);

//            it will know we are using single table inheritance strategy
//            and it has discriminator col DTYPE
            var sql = "SELECT p FROM Book p";
            em.createQuery(sql, Book.class)
                    .getResultList().forEach(System.out::println);

//            union works with native query, but it doestn supports jpql


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