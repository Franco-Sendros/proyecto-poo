package com.prueba.demo.Repositories;

import com.prueba.demo.Models.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByName(String name); // Método para encontrar un usuario por nombre de usuario
}