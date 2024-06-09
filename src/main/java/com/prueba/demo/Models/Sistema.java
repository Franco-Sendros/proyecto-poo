package com.prueba.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "sistemas")
public class Sistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long systemId;

    @Column(name = "nombre_sistema", nullable = false)
    private String systemName;

    @OneToMany(mappedBy = "sistema", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permiso> permisos;

}
