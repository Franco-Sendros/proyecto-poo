package com.prueba.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "permisos")
public class Permiso implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="permiso_sequence")
    @SequenceGenerator(name="permiso_sequence", sequenceName="permiso_sequence", allocationSize=100)
    @Column(name = "idpermisos")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sistema", nullable = false)
    private Sistema sistema;
    
}