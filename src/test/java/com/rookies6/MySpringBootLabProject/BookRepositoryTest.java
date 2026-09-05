package com.rookies6.MySpringBootLabProject;

import com.rookies6.MySpringBootLabProject.entity.Book;
import com.rookies6.MySpringBootLabProject.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest // @DataJpaTest와 차이 : 실제 DB에 반영 되지 않고, 테스트가 끝나면 데이터가 사라짐.
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    //도서 등록 테스트
    @Test
    void testCreateBook(){
        //준비단계
        Book book = new Book();
        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPublishDate(LocalDate.of(2025, 04, 30));
        //book.setPublishDate(LocalDate.parse("2025-05-07"));
        book.setPrice(35000);
        System.out.println(book);
        //실행단계
        Book addBook = bookRepository.save(book);
        //검증단계
        assertThat(addBook).isNotNull();
        assertThat(addBook.getTitle()).isEqualTo("JPA 프로그래밍");
    }

    //ISBN으로 도서 조회 테스트
    void testFindByIsbn() {
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746425");
        if(optionalBook.isPresent()){
            Book existBook = optionalBook.get();
            assertThat(existBook.getIsbn()).isEqualTo("9788956746425");
        }

        optionalBook.ifPresent(book -> System.out.println(book.getTitle()));
    }

    //3. 저자명으로 도서 목록 조회 테스트
    @Test
    void testFindByAuthor() {
        List<Book> listBooks = bookRepository.findByAuthor("박둘리");
        assertThat(listBooks).isNotEmpty();
        listBooks.forEach(book -> {
            assertThat(book.getAuthor()).isEqualTo("박둘리");
            System.out.println(book.getTitle());
        });
    }

    //도서 정보 수정 테스트
    @Test
    void testUpdate() {
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseGet(() -> new Book());
        //setter 호출. dirty checking
        book.setAuthor("구길동");
        Book updatedBook = bookRepository.save(book);
        assertThat(book.getAuthor()).isEqualTo("구길동");
    }

    @Test
    void testDeleteBook() {
        //준비단계: isbn으로 삭제할 도서
        Book book = bookRepository.findByIsbn("9788956746432")
                .orElseThrow(() -> new RuntimeException("Book Not Found"));
        Long id = book.getId();
        //실행단계
        bookRepository.deleteById(id);
        //검증단계
        Optional<Book> deletedBook = bookRepository.findById(id);
        assertThat(deletedBook).isEmpty();
    }

}
