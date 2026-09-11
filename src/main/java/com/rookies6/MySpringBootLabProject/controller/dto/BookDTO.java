package com.rookies6.MySpringBootLabProject.controller.dto;

import com.rookies6.MySpringBootLabProject.entity.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Book title is required")
        @Size(max = 100, message = "Book title cannot exceed 100 characters")
        private String title;

        @NotBlank(message = "Book author is required")
        @Size(max = 50, message = "Book author cannot exceed 50 characters")
        private String author;

        @NotBlank(message = "Book isbn is required")
        @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN must be 10 or 13 digits")
        private String isbn;

        @NotNull(message = "Book price is required")
        @PositiveOrZero(message = "Book price cannot be negative")
        private Integer price;

        @NotNull(message = "Book publishDate is required")
        @PastOrPresent(message = "Book publishDate cannot be in the future")
        private LocalDate publishDate;

        @Valid
        private BookDetailDTO detailRequest;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailDTO {
        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        private String description;

        private String language;

        @PositiveOrZero(message = "Page count cannot be negative")
        private Integer pageCount;

        private String publisher;

        private String coverImageUrl;

        private String edition;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailResponse detail;

        public static Response fromEntity(Book book) {
            BookDetailResponse detailResponse = book.getBookDetail() != null
                    ? BookDetailResponse.builder()
                    .id(book.getBookDetail().getId())
                    .description(book.getBookDetail().getDescription())
                    .language(book.getBookDetail().getLanguage())
                    .pageCount(book.getBookDetail().getPageCount())
                    .publisher(book.getBookDetail().getPublisher())
                    .coverImageUrl(book.getBookDetail().getCoverImageUrl())
                    .edition(book.getBookDetail().getEdition())
                    .build()
                    : null;

            return Response.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .detail(detailResponse)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailResponse {
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PatchRequest {
        @Size(max = 100, message = "Book title cannot exceed 100 characters")
        private String title;

        @Size(max = 50, message = "Book author cannot exceed 50 characters")
        private String author;

        @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN must be 10 or 13 digits")
        private String isbn;

        @PositiveOrZero(message = "Book price cannot be negative")
        private Integer price;

        @PastOrPresent(message = "Book publishDate cannot be in the future")
        private LocalDate publishDate;

        @Valid
        private BookDetailPatchRequest detailRequest;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailPatchRequest {
        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        private String description;

        private String language;

        @PositiveOrZero(message = "Page count cannot be negative")
        private Integer pageCount;

        private String publisher;

        private String coverImageUrl;

        private String edition;
    }

}