//package com.rookies6.MySpringBootLabProject.controller;
//
//import com.rookies6.MySpringBootLabProject.entity.Book;
//import com.rookies6.MySpringBootLabProject.exception.BusinessException;
//import com.rookies6.MySpringBootLabProject.exception.ErrorCode;
//import com.rookies6.MySpringBootLabProject.repository.BookRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@Slf4j
//@RequestMapping("/api/books")
//@RequiredArgsConstructor
//public class BookRestController {
//    private final BookRepository bookRepository;
//
//    @PostMapping
//    public Book createBook(@RequestBody Book bookDetail){
//        return bookRepository.save(bookDetail);
//    }
//
//    @GetMapping
//    public List<Book> getBooks(){
////        return bookRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Book getBookById(@PathVariable Long id){
//        Optional<Book> optionalBook = bookRepository.findById(id);
//        Book existBook = getBook(optionalBook);
//        return existBook;
//    }
//
//    public static Book getBook(Optional<Book> optionalBook){
//        Book existBook = optionalBook.orElseThrow(
//                ()->new BusinessException("Book Not found", HttpStatus.NOT_FOUND));
//        return existBook;
//    }
//
//    @GetMapping("/isbn/{isbn}")
//    public Book getBookByIsbn(@PathVariable String isbn){
//        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
//        Book bookByIsbn = optionalBook.orElseThrow(
//                ()->new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "isbn", isbn));
//        //Book existBook = getBook(bookRepository.findByIsbn(isbn));
//        return bookByIsbn;
//    }
//
//    @PatchMapping("/{id}") // patch 부분 수정, 전체 수정은 put
//    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetail){
//        Book existBook = getBook(bookRepository.findById(id));
//        existBook.setTitle(bookDetail.getTitle());
//        existBook.setAuthor(bookDetail.getAuthor());
//        existBook.setPrice(bookDetail.getPrice());
//        existBook.setPublishDate(bookDetail.getPublishDate());
//        return bookRepository.save(existBook);
//    }
//
//    @DeleteMapping("/{id}") // 삭제
//    public ResponseEntity<?> deleteBook(@PathVariable Long id){ //ResponseEntity = 상태코드+헤더+응답본문
//        //id 값 조회해서 책 가져오기
//        Book existBook = getBook(bookRepository.findById(id));
//        //삭제
//        bookRepository.delete(existBook);
//        //삭제 완료 메세지 보여주기
//        //public static <T> ResponseEntity<T> ok(@Nullable T body) {return ok().<T>body(body);
//        //}
//        return ResponseEntity.ok("Id = " + id + "Book이 삭제 되었습니다." );
//    }
//
//
//}
