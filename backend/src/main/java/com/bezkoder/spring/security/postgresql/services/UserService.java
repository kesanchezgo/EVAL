package com.bezkoder.spring.security.postgresql.services;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.repository.UserRepository;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para buscar un usuario por su ID
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

}
