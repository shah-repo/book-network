package com.shah.book.history;

import com.shah.book.book.Book;
import com.shah.book.common.BaseEntity;
import com.shah.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookTransactionHistory extends BaseEntity {

    private boolean returned;
    private boolean returnApproved;

    // User relationship
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Book relationship
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
