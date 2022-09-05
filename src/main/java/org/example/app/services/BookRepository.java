package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book> {
    private final Logger logger = LogManager.getRootLogger();
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeByPattern(Pattern pattern) {
        boolean somethingRemoved = false;

        boolean isAuthorMatch;
        boolean isTitleMatch;
        boolean isSizeMatch;

        for (Book book : retreiveAll()) {
            isAuthorMatch = pattern.matcher(book.getAuthor()).find();
            isTitleMatch = pattern.matcher(book.getTitle()).find();
            isSizeMatch = pattern.matcher(String.valueOf(book.getSize())).find();

            if (isAuthorMatch || isTitleMatch || isSizeMatch) {
                somethingRemoved = repo.remove(book);
            }
        }

        return somethingRemoved;
    }
}
