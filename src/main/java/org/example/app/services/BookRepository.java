package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.dto.Book;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book> {
    private final Logger logger = LogManager.getRootLogger();

    private final NamedParameterJdbcTemplate jbdcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jbdcTemplate) {
        this.jbdcTemplate = jbdcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        return jbdcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
    }

    @Override
    public void store(@NotNull Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());

        jbdcTemplate.update("INSERT INTO books(author, title, size) VALUES (:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);

        jbdcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed");
        return true;
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
                MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                parameterSource.addValue("id", book.getId());
                jbdcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
                somethingRemoved = true;
            }
        }

        return somethingRemoved;
    }
}
