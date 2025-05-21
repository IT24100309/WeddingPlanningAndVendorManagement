package com.weddingplanner.repositories;

import com.weddingplanner.models.User;
import java.io.IOException;
import java.util.Optional;

public class UserRepository extends AbstractFileRepository<User> {
    
    public UserRepository() {
        super("users", ".json", User.class);
    }

    @Override
    protected String getEntityId(User user) {
        return user.getId();
    }

    public Optional<User> findByEmail(String email) throws IOException {
        return findAll().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }
} 