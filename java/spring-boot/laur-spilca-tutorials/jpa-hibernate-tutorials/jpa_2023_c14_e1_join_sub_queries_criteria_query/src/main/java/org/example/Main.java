package org.example;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.example.entities.Author;
import org.example.entities.Book;
import org.example.entities.BookShop;
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

            CriteriaBuilder cb = em.getCriteriaBuilder();
/** ex 1
//            CriteriaQuery<Author> cq = cb.createQuery(Author.class);
            CriteriaQuery<Tuple> cq = cb.createTupleQuery();
            Root<Book> bookRoot = cq.from(Book.class); // select b from book b
//            Join<Book, Author> joinAuthor = bookRoot.join("authorsList");// authorsList is name of the attribute used in entity
            Join<Book, Author> joinAuthor = bookRoot.join("authorsList", JoinType.LEFT);
            Join<Book, BookShop> joinBookShop = bookRoot.join("bookShopList");
//            Join<Book, BookShop> joinBookShop = bookRoot.join("bookShopList", JoinType.LEFT);

//            cq.multiselect(bookRoot, joinAuthor); // select b, a from book b inner join author a
            cq.multiselect(bookRoot, joinAuthor, joinBookShop);

            TypedQuery<Tuple> q = em.createQuery(cq);
//            q.getResultStream().forEach(t -> System.out.println(t.get(0) + ", " + t.get(1)));
            q.getResultStream().forEach(t -> System.out.println(t.get(0) + ", " + t.get(1) + ", " + t.get(2)));
*/

//          select a, (select count(b) from book b join author a on b.id in a.bookslist) n from author a where n > 2
            CriteriaQuery<Author> mainQuery = cb.createQuery(Author.class);
            Root<Author> authorRoot = mainQuery.from(Author.class);

//            count is always a Long not Integer also
            Subquery<Long> subquery = mainQuery.subquery(Long.class);
            Root<Author> subRootAuthor = subquery.correlate(authorRoot);
            Join<Author, Book> authorBookJoin = subRootAuthor.join("booksList"); // booksList is the name of attribute
            subquery.select(cb.count(authorBookJoin));
            mainQuery.select(authorRoot)
                    .where(cb.greaterThan(subquery, 1L));

            TypedQuery<Author> q = em.createQuery(mainQuery);
            q.getResultStream()
                            .forEach(a -> System.out.println(a));

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