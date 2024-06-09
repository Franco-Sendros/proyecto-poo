package com.prueba.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.demo.Models.Sistema;



@Repository
public interface SistemaRepository extends JpaRepository<Sistema, Long> {
    Sistema findBySystemName(String systemName);
}
