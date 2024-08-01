package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Spring Boot 3");
        book.setAuthor("Author Name");
        book.setPublicationYear(2023);
        book.setIsbn("9781234567890");

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot 3"));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        Book book = new Book();
        book.setTitle("Spring Boot 3");
        book.setAuthor("Author Name");
        book.setPublicationYear(2023);
        book.setIsbn("9781234567891");
        book = bookRepository.save(book);

        mockMvc.perform(get("/api/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot 3"));
    }

    @Test
    void shouldReturnNotFoundForInvalidBookId() throws Exception {
        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBook() throws Exception {
        Book book = new Book();
        book.setTitle("Spring Boot 3");
        book.setAuthor("Author Name");
        book.setPublicationYear(2023);
        book.setIsbn("9781234567892");
        book = bookRepository.save(book);

        mockMvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isNoContent());
    }
}
