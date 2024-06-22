package com.prueba.demo.Controllers;

import com.prueba.demo.Models.Usuario;
import com.prueba.demo.Services.CRUDUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sistema_abm")
public class CRUDSystemController {

    @Autowired
    private CRUDUserService crudUserService;

    static public class UserForm{
        public String username;
        public String password;
        public String role;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserForm user) {
        return crudUserService.create(new Usuario(user.username, user.password, user.role));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Usuario user) {
        return crudUserService.update(id, user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return crudUserService.delete(id);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return crudUserService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return crudUserService.getUserById(id);
    }

}
