package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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
//        props.put("hibernate.hbm2ddl.auto", "create"); // create, none, update
        //        <property name="hibernate.format_sql" value="true"/>

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(
                        new CustomPersistenceUnitInfo(persistenceUnitName),
                        props
                );

        EntityManager em = emf.createEntityManager(); // never use hibernate directly

        try {
            em.getTransaction().begin();

/** Example 1
//            String jpql = "SELECT p FROM Product p";
//            String jpql = "SELECT p FROM Product p where p.price > 5";
            String jpql = "SELECT p FROM Product p where p.price = :price AND p.name LIKE :name";
//            avoid using native sql queries and Double. instead use BigDecimal
//            jpql allows to query from context. in jpql we query from context not from table
//            SELECT p FROM Product p ==> Fetch all attributes of Product entity from current context
//            SELECT * FROM Product ==> Fetch all cols from table Product
//            if we use Query q below as data type, we dont pass second argument and we have to manually map it
            TypedQuery<Product> productTypedQuery = em.createQuery(jpql, Product.class);

            productTypedQuery.setParameter("price", 5);
            productTypedQuery.setParameter("name", "%a%");

            List<Product> productList = productTypedQuery.getResultList();

            for (Product p: productList) {
                System.out.println(p);
            }
*/

/** Example 2 -
//            String jpql = "SELECT AVG(p.price) FROM Product p";
//            TypedQuery<Double> q = em.createQuery(jpql, Double.class);
//            Double avg = q.getSingleResult();

            String jpql = "SELECT COUNT(p) FROM Product p";
            TypedQuery<Long> q = em.createQuery(jpql, Long.class);

            Long avg = q.getSingleResult();

            System.out.println(avg);
*/

            /** Ex 3
            String jpql = "SELECT p.name, p.price FROM Product p";

            TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);

            q.getResultList().forEach(o -> System.out.println(o[0] + ", " + o[1]));
             */

            String jpql = "SELECT p FROM Product p WHERE p.name LIKE 'CANDY'";

            TypedQuery<Product> q = em.createQuery(jpql, Product.class);

            Optional<Product> p = null;
            try {
//                Product p = q.getSingleResult();
//                System.out.println(p);
                p = Optional.of(q.getSingleResult());
            } catch (NoResultException e) {
//                throw new RuntimeException(e);
                p = Optional.empty();
            }

//            System.out.println(p);
            p.ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Product not found")
            );

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