package com.prueba.demo.Controllers;

import com.prueba.demo.Models.Usuario;
import com.prueba.demo.Services.CRUDUserService;
import com.prueba.demo.Services.PanelService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sistema_abm")
public class CRUDSystemController {

    @Autowired
    private CRUDUserService crudUserService;

    @Autowired
    private PanelService panelService;

    static public class UserForm {
        public String username;
        public String password;
        public String role;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserForm user, HttpServletRequest request) {
        if (!panelService.checkSession(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return crudUserService.create(new Usuario(user.username, user.password, user.role));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Usuario user, HttpServletRequest request) {
        if (!panelService.checkSession(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return crudUserService.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, HttpServletRequest request) {
        if (!panelService.checkSession(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return crudUserService.delete(id);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        if (!panelService.checkSession(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return crudUserService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id, HttpServletRequest request) {
        if (!panelService.checkSession(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return crudUserService.getUserById(id);
    }
}

