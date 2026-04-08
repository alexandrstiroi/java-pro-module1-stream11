package org.shtiroy.module1.hm04.hikari.service;

import jakarta.persistence.EntityNotFoundException;
import org.shtiroy.module1.hm04.hikari.entity.User;
import org.shtiroy.module1.hm04.hikari.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(String username) {
        User user = new User(username);
        repository.save(user);
    }

    public Optional<User> getUser(Long id) {
        return repository.findById(id);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findLike(String username) {
        return repository.search(username);
    }
}
