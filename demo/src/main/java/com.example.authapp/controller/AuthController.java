package com.example.authapp.controller;

import com.example.authapp.model.User;
import com.example.authapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String email, @RequestParam String senha, Model model) {
        Optional<User> user = authService.authenticate(email, senha);
        if (user.isPresent()) {
            authService.generateAccessCode(user.get());
            model.addAttribute("email", email);
            return "verify"; // tela para inserir o código de acesso
        } else {
            model.addAttribute("error", "Credenciais inválidas");
            return "error";
        }
    }

    @PostMapping("/verify")
    public String verifyCode(@RequestParam String email, @RequestParam String codigo, HttpSession session, Model model) {
        Optional<User> user = authService.authenticate(email, "");
        if (user.isPresent() && authService.validateAccessCode(user.get(), codigo)) {
            session.setAttribute("user", user.get());
            return "success";
        } else {
            model.addAttribute("error", "Código inválido ou expirado");
            return "error";
        }
    }
}
