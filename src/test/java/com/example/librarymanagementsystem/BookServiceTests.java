package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.exception.EntityAlreadyExistsException;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void addBook_ShouldThrowException_WhenBookExists() {
        Book book = new Book();
        book.setIsbn("9781234567890");

        when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(true);

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            bookService.addBook(book);
        });

        assertEquals("Book with ISBN 9781234567890 already exists", exception.getMessage());
    }

    @Test
    void addBook_ShouldSaveBook_WhenBookDoesNotExist() {
        Book book = new Book();
        book.setTitle("Spring Boot 3");
        book.setAuthor("Author Name");
        book.setPublicationYear(2023);
        book.setIsbn("9781234567890");

        when(bookRepository.existsByIsbn(book.getIsbn())).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertNotNull(savedBook);
        assertEquals("Spring Boot 3", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot 3");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("Spring Boot 3", foundBook.get().getTitle());
    }

    @Test
    void getBookById_ShouldReturnEmpty_WhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertFalse(foundBook.isPresent());
    }
}
