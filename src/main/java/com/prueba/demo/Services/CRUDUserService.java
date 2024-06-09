package com.prueba.demo.Services;

import com.prueba.demo.Models.Usuario;
import com.prueba.demo.Repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
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

    public ResponseEntity<?> read(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return ResponseEntity.ok().body(usuario.get());
    }

    public ResponseEntity<?> update(long id, Usuario updatedUsuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario existingUsuario = optionalUsuario.get();
            existingUsuario.setName(updatedUsuario.getName());
            existingUsuario.setPassword(updatedUsuario.getPassword());
            existingUsuario.setRol(updatedUsuario.getRol());
            existingUsuario.setPermisos(updatedUsuario.getPermisos());
            existingUsuario.setSesiones(updatedUsuario.getSesiones());

            usuarioRepository.save(existingUsuario);
            return ResponseEntity.ok(existingUsuario);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Usuario no encontrado");
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
