package com.prueba.demo.Services;

import com.prueba.demo.Repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CRUDUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

}
