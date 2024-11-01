package com.example.authapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    private String codigoAcesso;
    private LocalDateTime codigoAcessoCreatedAt;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCodigoAcesso() { return codigoAcesso; }
    public void setCodigoAcesso(String codigoAcesso) { this.codigoAcesso = codigoAcesso; }

    public LocalDateTime getCodigoAcessoCreatedAt() { return codigoAcessoCreatedAt; }
    public void setCodigoAcessoCreatedAt(LocalDateTime codigoAcessoCreatedAt) {
        this.codigoAcessoCreatedAt = codigoAcessoCreatedAt;
    }
}
