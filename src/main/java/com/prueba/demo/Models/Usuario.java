package com.prueba.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;


@Entity
@Getter @Setter @ToString
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarios", nullable = false)
    private long id;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @Column(name = "contrasenia", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permiso> permisos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Token> sesiones;

    protected Usuario() {
    }

    public Usuario(String username, String password, String rol) {
        this.name = username;
        this.password = password;
        this.rol = rol;
    }
}
