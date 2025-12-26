package com.shah.book.book;

import com.shah.book.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String owner;
    private String synopsis;
    private byte[] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;
    private boolean isBorrowed;
}
