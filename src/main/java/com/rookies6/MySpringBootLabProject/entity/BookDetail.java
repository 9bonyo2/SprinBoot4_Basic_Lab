package com.rookies6.MySpringBootLabProject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
//Owner(주인) - FK를 가진 쪽이 주인임
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column(length = 1000)
    private String description;

    @Column
    private String language;

    @Column
    private Integer pageCount;

    @Column
    private String publisher;

    @Column
    private String coverImageUrl;

    @Column
    private String edition;

    //1:1 지연로딩
    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn은 FK(외래키)에 해당하는 애노테이션
    //FK를 가진 BookDetail 객체가 주인(Owner)이다
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
}