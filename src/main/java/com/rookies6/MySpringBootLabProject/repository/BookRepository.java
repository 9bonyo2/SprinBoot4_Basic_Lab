package com.rookies6.MySpringBootLabProject.repository;


import com.rookies6.MySpringBootLabProject.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //LEFT JOIN FETCH : 상세정보가 없는 책도 조회되어야 하므로 외부 조인 사용
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    //전체 목록 조회 - N+1 문제 방지
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail")
    List<Book> findAllWithBookDetail();

    boolean existsByIsbn(String isbn);

    List<Book> findByAuthorContaining(String author);

    List<Book> findByTitleContaining(String title);
}