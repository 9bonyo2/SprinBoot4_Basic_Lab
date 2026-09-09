package com.rookies6.MySpringBootLabProject.service;

import com.rookies6.MySpringBootLabProject.controller.dto.BookDTO;
import com.rookies6.MySpringBootLabProject.entity.Book;
import com.rookies6.MySpringBootLabProject.exception.BusinessException;
import com.rookies6.MySpringBootLabProject.exception.ErrorCode;
import com.rookies6.MySpringBootLabProject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDTO.BookResponse::fromEntity)
                .toList();
    }

    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));
        return BookDTO.BookResponse.fromEntity(book);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "isbn", isbn));
        return BookDTO.BookResponse.fromEntity(book);
    }

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        // isbn 중복 체크
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publishDate(request.getPublishDate())
                .price(request.getPrice())
                .build();

        Book savedBook = bookRepository.save(book);
        return BookDTO.BookResponse.fromEntity(savedBook);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        // isbn은 수정 대상이 아니므로 중복 체크 불필요
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishDate(request.getPublishDate());
        book.setPrice(request.getPrice());

        Book updatedBook = bookRepository.save(book);
        return BookDTO.BookResponse.fromEntity(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Book", "id", id);
        }
        bookRepository.deleteById(id);
    }
}