package com.prueba.demo.Services;

import com.prueba.demo.Models.Permiso;
import com.prueba.demo.Models.Token;
import com.prueba.demo.Models.Usuario;
//import com.prueba.demo.Repositories.PermisosRepository;
//import com.prueba.demo.Repositories.TokenRepository;
import com.prueba.demo.Repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CRUDUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    /* 
    @Autowired
    private PermisosRepository permisosRepository;

    @Autowired
    private TokenRepository tokenRepository;
    */
    public ResponseEntity<?> getAllUsers(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    public ResponseEntity<?> getUserById(long id){
        return ResponseEntity.ok(usuarioRepository.findById(id));
    }

    public ResponseEntity<?> create(Usuario usuario) {
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> update(long id, Usuario updatedUser) {
        Optional<Usuario> existingUserOpt = usuarioRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Usuario existingUser = existingUserOpt.get();

            existingUser.setName(updatedUser.getName());
            existingUser.setRol(updatedUser.getRol());

            existingUser.getPermisos().clear();
            for (Permiso permiso : updatedUser.getPermisos()) {
                permiso.setUsuario(existingUser);
                existingUser.getPermisos().add(permiso);
            }

            existingUser.getSesiones().clear();
            for (Token sesion : updatedUser.getSesiones()) {
                sesion.setUsuario(existingUser);
                existingUser.getSesiones().add(sesion);
            }

            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            usuarioRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<?> delete(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            usuarioRepository.delete(optionalUsuario.get());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Usuario no encontrado");
        }
        return ResponseEntity.ok().build();
    }
}
