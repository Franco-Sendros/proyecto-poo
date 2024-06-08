package com.prueba.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sesiones")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsesiones")
    private Long id;

    @Column(name = "token", nullable = false, length = 255)
    private String token;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "inicio", nullable = false)
    private Date createdAt;

    @Column(name = "expira", nullable = false)
    private Date expiresAt;


}