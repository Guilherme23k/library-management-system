package com.librarymanagementsystem.service;

import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.repository.BookRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetUserById(){

        Book book = Instancio.of(Book.class)
                .set(Select.field(Book::getTitle), "Livro")
                .set(Select.field(Book::getAuthor), "Guilherme")
                .set(Select.field(Book::getIsbn), "9783127323207")
                .create();

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book retriviedBook = bookService.getBookById(1L);

        Assertions.assertNotNull(retriviedBook);
        Assertions.assertEquals("Livro", retriviedBook.getTitle());
        Assertions.assertEquals("Guilherme", retriviedBook.getAuthor());
        Assertions.assertEquals("9783127323207", retriviedBook.getIsbn());

    }

    @Test
    public void testRegisterBook(){
        Book bookToRegister = Instancio.of(Book.class)
                .set(Select.field(Book::getTitle), "Livro")
                .set(Select.field(Book::getAuthor), "Guilherme")
                .set(Select.field(Book::getIsbn), "9783127323207")
                .create();

        Mockito.when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(invocation -> {
                    Book book = invocation.getArgument(0);
                    book.setId(1L);
                    return book;
                });

        Book registeredBook = bookService.register(bookToRegister);

        Assertions.assertNotNull(registeredBook.getId(), "User id cannot be null");
        Assertions.assertEquals("Livro", registeredBook.getTitle(), "Incorretly registered book title");
        Assertions.assertEquals("Guilherme", registeredBook.getAuthor(), "Incorretly registered author");
        Assertions.assertEquals("9783127323207", registeredBook.getIsbn(), "Incorretly registered ISBN");
    }

    @Test
    public void testGetAllBooks(){
        Stream<Book> bookStream = Instancio.stream(Book.class).limit(10);

        List<Book> expectedBook = bookStream.collect(Collectors.toList());

        Mockito.when(bookRepository.findAll()).thenReturn(expectedBook);

        List<Book> actualBooks = bookService.getAllBooks();

        Assertions.assertEquals(expectedBook, actualBooks);
    }

}
