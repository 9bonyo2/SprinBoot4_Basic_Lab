package com.rookies6.MySpringBootLabProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDate publishDate;

    //1:1 지연로딩
    //mappedBy에는 상대 엔티티(BookDetail)에 있는 필드명을 적는다
    //cascade = ALL : Book 저장/삭제 시 BookDetail도 함께 처리된다
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "book",
            cascade = CascadeType.ALL)
    private BookDetail bookDetail;
}