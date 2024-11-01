package com.example.authapp.service;

import com.example.authapp.model.User;
import com.example.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String email, String senha) {
        User user = new User();
        user.setEmail(email);
        user.setSenha(passwordEncoder.encode(senha));
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String senha) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(senha, user.get().getSenha())) {
            return user;
        }
        return Optional.empty();
    }

    public String generateAccessCode(User user) {
        String code = String.format("%06d", new Random().nextInt(999999));
        user.setCodigoAcesso(code);
        user.setCodigoAcessoCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return code;
    }

    public boolean validateAccessCode(User user, String code) {
        if (user.getCodigoAcesso().equals(code) &&
                user.getCodigoAcessoCreatedAt().isAfter(LocalDateTime.now().minusMinutes(5))) {
            return true;
        }
        return false;
    }
}
