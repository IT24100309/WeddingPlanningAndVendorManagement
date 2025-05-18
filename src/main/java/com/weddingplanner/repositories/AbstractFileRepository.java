package com.weddingplanner.repositories;

import com.weddingplanner.utils.JsonUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;
import java.util.Objects;

// ← These two are required for your findAll() stream pipeline:
import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.util.concurrent.locks.ReentrantReadWriteLock;


public abstract class AbstractFileRepository<T> implements FileRepository<T> {
    protected final Path dataDirectory;
    protected final String fileExtension;
    protected final Class<T> entityClass;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    protected AbstractFileRepository(String directoryPath, String fileExtension, Class<T> entityClass) {
        this.dataDirectory = Paths.get(System.getProperty("user.home"), "weddingplanner", directoryPath);
        this.fileExtension = fileExtension;
        this.entityClass = entityClass;
        initializeDirectory();
    }

    private void initializeDirectory() {
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory: " + dataDirectory, e);
        }
    }

    @Override
    public void save(T entity) throws IOException {
        String id = getEntityId(entity);
        Path filePath = dataDirectory.resolve(id + fileExtension);

        lock.writeLock().lock();
        try {
            String json = JsonUtils.toJson(entity);
            Files.writeString(filePath, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public T findById(String id) throws IOException {
        Path filePath = dataDirectory.resolve(id + fileExtension);

        lock.readLock().lock();
        try {
            if (!Files.exists(filePath)) {
                return null;
            }
            String json = Files.readString(filePath);
            return JsonUtils.fromJson(json, entityClass);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<T> findAll() throws IOException {
        lock.readLock().lock();
        try {
            try (Stream<Path> files = Files.list(dataDirectory)) {
                return files
                    .filter(path -> path.toString().endsWith(fileExtension))
                    .map(this::readFile)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void update(T entity) throws IOException {
        save(entity); // Since we're using file-based storage, save and update are the same
    }

    @Override
    public void delete(String id) throws IOException {
        Path filePath = dataDirectory.resolve(id + fileExtension);

        lock.writeLock().lock();
        try {
            Files.deleteIfExists(filePath);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private T readFile(Path path) {
        try {
            String json = Files.readString(path);
            return JsonUtils.fromJson(json, entityClass);
        } catch (IOException e) {
            return null;
        }
    }

    protected abstract String getEntityId(T entity);
}