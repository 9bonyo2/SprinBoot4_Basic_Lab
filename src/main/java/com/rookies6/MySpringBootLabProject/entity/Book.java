package com.rookies6.MySpringBootLabProject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private LocalDate publishDate;

    @Column(nullable = false)
    private int price;


}