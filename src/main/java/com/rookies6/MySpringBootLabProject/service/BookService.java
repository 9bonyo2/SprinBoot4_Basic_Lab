package com.rookies6.MySpringBootLabProject.service;

import com.rookies6.MySpringBootLabProject.controller.dto.BookDTO;
import com.rookies6.MySpringBootLabProject.entity.Book;
import com.rookies6.MySpringBootLabProject.entity.BookDetail;
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

    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAllWithBookDetail()
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));
        return BookDTO.Response.fromEntity(book);
    }

    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "isbn", isbn));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> searchByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author)
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public List<BookDTO.Response> searchByTitle(String title) {
        return bookRepository.findByTitleContaining(title)
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    //주인(Owner) 쪽 설정 - 이 값이 외래키로 저장된다
                    .book(book)
                    .build();
            //비주인 쪽 설정 - 양방향 객체 상태를 맞춘다
            book.setBookDetail(bookDetail);
        }

        //cascade = ALL 이므로 Book만 저장하면 BookDetail도 함께 저장된다
        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(savedBook);
    }

    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        //isbn이 바뀌는 경우에만 중복 체크 (Student의 학번 로직과 동일 패턴)
        if (!book.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = book.getBookDetail();

            //상세정보가 아직 없으면 새로 만들어 양방향으로 연결한다
            if (bookDetail == null) {
                bookDetail = new BookDetail();
                bookDetail.setBook(book);       //주인 쪽
                book.setBookDetail(bookDetail); //비주인 쪽
            }

            bookDetail.setDescription(request.getDetailRequest().getDescription());
            bookDetail.setLanguage(request.getDetailRequest().getLanguage());
            bookDetail.setPageCount(request.getDetailRequest().getPageCount());
            bookDetail.setPublisher(request.getDetailRequest().getPublisher());
            bookDetail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            bookDetail.setEdition(request.getDetailRequest().getEdition());
        }

        Book updatedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Book", "id", id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public BookDTO.Response patchBook(Long id, BookDTO.PatchRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        // isbn을 실제로 바꾸려는 경우에만 중복 체크
        if (request.getIsbn() != null &&
                !book.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        // null 체크를 통해 제공된 필드만 업데이트, 제공 안 된 필드는 기존 값 유지
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }

        // detailRequest가 왔으면 BookDetail도 부분 수정
        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = book.getBookDetail();
            if (bookDetail == null) {
                bookDetail = new BookDetail();
                bookDetail.setBook(book);
                book.setBookDetail(bookDetail);
            }
            applyDetailPatch(bookDetail, request.getDetailRequest());
        }

        Book updatedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    @Transactional
    public BookDTO.Response patchBookDetail(Long id, BookDTO.BookDetailPatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        BookDetail bookDetail = book.getBookDetail();
        if (bookDetail == null) {
            bookDetail = new BookDetail();
            bookDetail.setBook(book);
            book.setBookDetail(bookDetail);
        }
        applyDetailPatch(bookDetail, request);

        Book updatedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    // 두 메서드가 공통으로 쓰는 부분 수정 로직 (중복 제거)
    private void applyDetailPatch(BookDetail bookDetail, BookDTO.BookDetailPatchRequest request) {
        if (request.getDescription() != null) {
            bookDetail.setDescription(request.getDescription());
        }
        if (request.getLanguage() != null) {
            bookDetail.setLanguage(request.getLanguage());
        }
        if (request.getPageCount() != null) {
            bookDetail.setPageCount(request.getPageCount());
        }
        if (request.getPublisher() != null) {
            bookDetail.setPublisher(request.getPublisher());
        }
        if (request.getCoverImageUrl() != null) {
            bookDetail.setCoverImageUrl(request.getCoverImageUrl());
        }
        if (request.getEdition() != null) {
            bookDetail.setEdition(request.getEdition());
        }
    }
}