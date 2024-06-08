package com.prueba.demo.Models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuario_sequence")
    @SequenceGenerator(name="usuario_sequence", sequenceName="usuario_sequence", allocationSize=100)
    @Column(name = "idusuarios")
    private Long id;

    @Column(name = "nombre_usuario", length = 255, nullable = false)
    private String name;

    @Column(name = "contrasenia", length = 255, nullable = false)
    private String password;

    @Column(name = "rol", length = 255, nullable = false)
    private String userType;

    @OneToMany(mappedBy = "usuario")
    private Set<Permiso> permisos;
}