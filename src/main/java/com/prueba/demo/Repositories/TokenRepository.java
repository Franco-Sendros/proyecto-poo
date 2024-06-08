package com.prueba.demo.Repositories;

import com.prueba.demo.Models.Token;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    void deleteByExpiresAtBefore(Date now);

    Token findByToken(String token);


}