package com.prueba.demo.Repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.demo.Models.Permiso;
import com.prueba.demo.Models.Usuario;

@Repository
public interface PermisosRepository extends JpaRepository<Permiso, Long> {
    Set<Permiso> findByUsuario(Usuario usuario);
}
