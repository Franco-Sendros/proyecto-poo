package com.prueba.demo.Models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

import lombok.*;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String name;

    @Column(name = "contrasenia", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permiso> permisos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Token> sesiones;

}
