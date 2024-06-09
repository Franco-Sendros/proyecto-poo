package com.prueba.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@Table(name = "sesiones")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idsesiones;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "inicio", nullable = false)
    private Date createdAt;

    @Column(name = "expira", nullable = false)
    private Date expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

}
