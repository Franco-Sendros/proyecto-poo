
package com.prueba.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.prueba.demo.Repositories.TokenRepository;

import jakarta.transaction.Transactional;

import java.util.Date;

@Service
@Transactional
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Scheduled(fixedRate = 3600000) // Ejecutar cada hora
    public void limpiarTokensVencidos() {
        Date now = new Date();
        tokenRepository.deleteByExpiresAtBefore(now);
    }

   
}