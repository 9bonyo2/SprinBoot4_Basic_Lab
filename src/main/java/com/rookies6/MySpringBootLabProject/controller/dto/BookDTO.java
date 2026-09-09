package com.rookies6.MySpringBootLabProject.controller.dto;

import com.rookies6.MySpringBootLabProject.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class BookDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookCreateRequest {
        @NotBlank(message = "book name is required")
        @Size(max = 100, message = "Book name cannot exceed 100 characters")
        private String title;

        @NotBlank(message = "author is required")
        @Size(max = 20, message = "Book author cannot exceed 20 characters")
        private String author;

        @NotBlank(message = "isbn is required")
        @Size(max = 20, message = "Book isbn cannot exceed 20 characters")
        private String isbn;

        @NotNull(message = "publishDate is required")
        private LocalDate publishDate;

        @NotNull(message = "price is required")
        @Positive(message = "Book price must be positive")
        private Integer price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookUpdateRequest {
        @NotBlank(message = "Book title is required")
        @Size(max = 100, message = "Book title cannot exceed 100 characters")
        private String title;

        @NotBlank(message = "Book author is required")
        @Size(max = 20, message = "Book author cannot exceed 20 characters")
        private String author;

        @NotNull(message = "Book publishDate is required")
        private LocalDate publishDate;

        @NotNull(message = "Book price is required")
        @Positive(message = "Book price must be positive")
        private Integer price;
    }

    //inner class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private LocalDate publishDate;
        private int price;

        public static BookResponse fromEntity(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .publishDate(book.getPublishDate())
                    .price(book.getPrice())
                    .build();
        }
    }

}
