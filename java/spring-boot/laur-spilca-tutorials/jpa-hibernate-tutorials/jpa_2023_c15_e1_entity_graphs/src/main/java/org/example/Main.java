package org.example;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.example.entities.Author;
import org.example.entities.Book;
import org.example.entities.BookShop;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.*;
import java.util.stream.Collectors;

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

//            Author -> Book -> BookShop
//            n + 1 problem, below ex generates n + 1 query
//            one of the ways to solve it is entity graphs
//            em.createQuery("SELECT a FROM Author a", Author.class)
//                    .getResultList()
//                    .forEach(a -> System.out.println(a.getBooksList()));

////            Author -> Book
//            EntityGraph<?> graph = em.createEntityGraph(Author.class);
////            booksList -> name of the attribute used in relationship in entity
//            graph.addAttributeNodes("booksList");
//            em.createQuery("SELECT a FROM Author a", Author.class)
//                    .setHint("jakarta.persistence.loadgraph", graph)
//                    .getResultList()
//                    .forEach(a -> System.out.println(a.getBooksList()));

//            sub graph
//            Author -> Book -> BookShop
//            EntityGraph<?> graph = em.createEntityGraph(Author.class);
//            Subgraph<?> subgraph = graph.addSubgraph("booksList");
////            booksList -> name of the attribute used in relationship in entity
//            subgraph.addAttributeNodes("bookShopList");

//            ex 3 using named entity graphs
            EntityGraph<?> graph = em.getEntityGraph("Author.eagerlyFetchBookShops");

            em.createQuery("SELECT a FROM Author a", Author.class)
                    .setHint("jakarta.persistence.loadgraph", graph)
                    .getResultList()
                    .forEach(a ->
                            System.out.println(
                                    a.getBooksList()
                                            .stream()
                                            .map(b -> b.getBookShopList())
                                            .collect(Collectors.toList())
                            )
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