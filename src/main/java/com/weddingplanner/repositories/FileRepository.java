package com.weddingplanner.repositories;

import java.io.IOException;
import java.util.List;

public interface FileRepository<T> {
    void save(T entity) throws IOException;
    T findById(String id) throws IOException;
    List<T> findAll() throws IOException;
    void update(T entity) throws IOException;
    void delete(String id) throws IOException;
}
