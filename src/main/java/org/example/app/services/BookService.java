package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = LogManager.getRootLogger();

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(String bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBooksByPattern(Pattern pattern) {
        return bookRepo.removeByPattern(pattern);
    }

    private void defaultInit() {
        logger.info("default INIT in book service");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in book service");
    }
}
