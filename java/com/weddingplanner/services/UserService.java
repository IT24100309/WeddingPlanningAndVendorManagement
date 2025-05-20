package com.weddingplanner.services;

import com.weddingplanner.models.User;
import com.weddingplanner.utils.FileHandler;
import com.weddingplanner.utils.PasswordUtil;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private final String dataFile;
    private final FileHandler fh;

    public UserService(ServletContext ctx) {
        this.fh       = new FileHandler();
        this.dataFile = ctx.getRealPath("/Data/users.txt");
        List<String> lines = fh.readFromFile(dataFile);
        for (String line : lines) {
            String[] p = line.split(",", -1);
            if (p.length == 4) {
                users.add(new User(p[0], p[1], p[2], p[3]));
            }
        }
    }

    private void save() {
        List<String> out = new ArrayList<>();
        for (User u : users) out.add(u.toString());
        fh.writeToFile(dataFile, out);
    }

    public boolean register(User u) {
        if (findByUsername(u.getUsername()) != null) return false;
        users.add(u);
        save();
        return true;
    }

    public User authenticate(String username, String password) {
        User u = findByUsername(username);
        if (u != null && PasswordUtil.matches(password, u.getPasswordHash())) {
            return u;
        }
        return null;
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }
}
