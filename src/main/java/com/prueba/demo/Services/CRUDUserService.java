package com.prueba.demo.Services;

import com.prueba.demo.Models.Permiso;
import com.prueba.demo.Models.Usuario;
import com.prueba.demo.Repositories.SistemaRepository;
import com.prueba.demo.Repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CRUDUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SistemaRepository sistemaRepository;

    private void asignarPermisos(Usuario usuario) {

        if (usuario.getPermisos() == null) { usuario.setPermisos(new HashSet<>()); }
        else { usuario.getPermisos().clear(); }

        switch (usuario.getRol()) {
            case "ADMIN":
                usuario.getPermisos().add(new Permiso(usuario, sistemaRepository.findBySystemName("PANEL_USUARIOS")));
                usuario.getPermisos().add(new Permiso(usuario, sistemaRepository.findBySystemName("GESTION_ACADEMICA")));
                usuario.getPermisos().add(new Permiso(usuario, sistemaRepository.findBySystemName("AUTOGESTION")));
                break;
            case "PROFESSOR":
                usuario.getPermisos().add(new Permiso(usuario, sistemaRepository.findBySystemName("AUTOGESTION")));
                break;
            case "STUDENT":
                usuario.getPermisos().add(new Permiso(usuario, sistemaRepository.findBySystemName("AUTOGESTION")));
                break;
        }

    }

    public ResponseEntity<?> getAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    public ResponseEntity<?> getUserById(long id) {
        return ResponseEntity.ok(usuarioRepository.findById(id));
    }

    public ResponseEntity<?> create(Usuario usuario) {

        Optional<Usuario> checkUser = Optional.ofNullable(usuarioRepository.findByName(usuario.getName()));
        if (checkUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe");
        }
        Usuario newUser = usuarioRepository.save(usuario);
        asignarPermisos(newUser);
        usuarioRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity<?> createStudent(Usuario usuario) {

        Optional<Usuario> checkUser = Optional.ofNullable(usuarioRepository.findByName(usuario.getName()));
        if (checkUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe");
        }

        Usuario newUser = usuarioRepository.save(usuario);
        asignarPermisos(newUser);
        usuarioRepository.save(newUser);
        return ResponseEntity.ok(newUser.getId());
    }

    public ResponseEntity<?> update(long id, Usuario updatedUser) {
        Optional<Usuario> existingUserOpt = usuarioRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Usuario existingUser = existingUserOpt.get();
            Usuario checkUser = usuarioRepository.findByName(updatedUser.getName());
            if (checkUser != null && checkUser.getId() != existingUser.getId()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe");
            }

            existingUser.setName(updatedUser.getName() != null ? updatedUser.getName() : existingUser.getName());

            existingUser.setRol(updatedUser.getRol() != null ? updatedUser.getRol() : existingUser.getRol());

            existingUser.setPassword(updatedUser.getPassword() != null ? updatedUser.getPassword() : existingUser.getPassword());

            asignarPermisos(existingUser);

            usuarioRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> delete(long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            usuarioRepository.delete(optionalUsuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        return ResponseEntity.ok().build();
    }

}
