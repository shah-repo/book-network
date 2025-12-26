package com.shah.book.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    @Query("""
            SELECT book
            FROM Book book
            WHERE book.archived = false
            AND book.shareable = true
            AND book.owner.id != :userId
            AND NOT EXISTS (
                SELECT 1
                FROM BookTransactionHistory bth
                WHERE bth.book.id = book.id
                AND bth.user.id = :userId
                AND bth.returnApproved = false
            )
            """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);
}
