package com.prueba.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "sistemas")
public class Sistema implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sistema_sequence")
    @SequenceGenerator(name="sistema_sequence", sequenceName="sistema_sequence", allocationSize=100)
    @Column(name = "idsistemas")
    private Long id;

    @Column(name = "nombre_sistema")
    private String nameSystem;

    @OneToMany(mappedBy = "sistema")
    private Set<Permiso> permisos;
    
}