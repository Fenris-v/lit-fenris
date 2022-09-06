package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.dto.Book;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {
    private final Logger logger = LogManager.getRootLogger();
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(@NotNull Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdToRemove) {
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

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit() {
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in book repo bean");
    }
}
