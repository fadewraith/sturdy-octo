package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//        2 ways to get an entity manager factory
//        - if using a persistence class, then
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit"), name = my-persistence-unit in persistence.xml file

//        #method1
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
//        #method2
        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>()); // second param can be used to provide properties, but leaving empty here
        EntityManager em = emf.createEntityManager(); // represents the context

        try {
            em.getTransaction().begin();
            Product p = new Product();
            p.setId(2L);
            p.setName("Lenovo");

            em.persist(p); // add this to the context -> NOT AN INSERT QUERY

            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}