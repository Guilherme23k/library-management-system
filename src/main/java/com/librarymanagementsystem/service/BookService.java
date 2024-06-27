package com.librarymanagementsystem.service;

import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book register(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public boolean deleteById(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }

    public Book updateBook(Book book) {
        return bookRepository.findById(book.getId())
                .map(existingBook ->{
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setIsbn(book.getIsbn());
                    existingBook.setTitle(book.getTitle());
                    return bookRepository.save(existingBook);
                }).orElse(null);
    }
}
