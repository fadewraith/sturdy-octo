package com.socialapp.book.history;

import com.socialapp.book.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

//    @Query("""
//            SELECT history
//            FROM BookTransactionHistory history
//            WHERE history.user.id = :userId
//            """)
//    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);
//
//    @Query("""
//            SELECT history
//            FROM BookTransactionHistory history
//            WHERE history.book.owner.id = :userId
//            """)
//    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);
//
//    @Query("""
//            SELECT
//            (COUNT(*) > 0) AS isBorrowed
//            FROM BookTransactionHistory history
//            where history.user.id = :userId
//            AND history.book.id = :bookId
//            AND history.returnApproved = false
//            """)
//    boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);
//
//    @Query("""
//            SELECT transaction
//            FROM BookTransactionHistory transaction
//            WHERE transaction.user.id = :userId
//            AND transaction.book.id = :bookId
//            AND transaction.returned = false
//            AND transaction.returnApproved = false
//            """)
//    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);
//
//
//    @Query("""
//            SELECT transaction
//            FROM BookTransactionHistory transaction
//            WHERE transaction.book.owner.id = :userId
//            AND transaction.book.id = :bookId
//            AND transaction.returned = true
//            AND transaction.returnApproved = false
//            """)
//    Optional<BookTransactionHistory> findByBookIdAndOwnerId(Integer bookId, Integer userId);

//    comments are after implementing keycloak changes
    @Query("""
            SELECT
            (COUNT (*) > 0) AS isBorrowed
            FROM BookTransactionHistory bookTransactionHistory
            WHERE bookTransactionHistory.userId = :userId
            AND bookTransactionHistory.book.id = :bookId
            AND bookTransactionHistory.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(@Param("bookId") Integer bookId, @Param("userId") String userId);

    @Query("""
            SELECT
            (COUNT (*) > 0) AS isBorrowed
            FROM BookTransactionHistory bookTransactionHistory
            WHERE bookTransactionHistory.book.id = :bookId
            AND bookTransactionHistory.returnApproved = false
            """)
    boolean isAlreadyBorrowed(@Param("bookId") Integer bookId);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory  transaction
            WHERE transaction.userId = :userId
            AND transaction.book.id = :bookId
            AND transaction.returned = false
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(@Param("bookId") Integer bookId, @Param("userId") String userId);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory  transaction
            WHERE transaction.book.createdBy = :userId
            AND transaction.book.id = :bookId
            AND transaction.returned = true
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(@Param("bookId") Integer bookId, @Param("userId") String userId);

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.userId = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, String  userId);
    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.createdBy = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, String userId);
}
