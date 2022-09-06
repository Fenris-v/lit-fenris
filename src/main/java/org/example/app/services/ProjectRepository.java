package org.example.app.services;

import java.util.List;
import java.util.regex.Pattern;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(String bookIdToRemove);

    boolean removeByPattern(Pattern pattern);
}
