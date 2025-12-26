package com.shah.book.book;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String borrowerName;
    private String ownerName;
    private String isbn;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
