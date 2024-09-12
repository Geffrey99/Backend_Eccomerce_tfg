package com.webG.service;

import com.webG.entity.Usuario;

import com.webG.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.security.Key;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario authenticate(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario; 
        }
        return null; 
    }
    
    public String generateToken(Usuario usuario) {
        long tiempoExpiracion = 1000 * 60 * 60; // 1 hora
        Key claveSegura = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
                .signWith(claveSegura) 
                .compact();
    }
}
