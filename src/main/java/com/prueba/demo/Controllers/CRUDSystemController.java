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

    static public class StudentForm{
        public String username;
        public String password;
    }

    static public class ChangePassForm{
        public String username;
        public String oldPassword;
        public String newPassword;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserForm user, HttpServletRequest request) {
        if (!panelService.checkSession(request)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return crudUserService.create(new Usuario(user.username, user.password, user.role));
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<?> registerStudent(@RequestBody StudentForm user, HttpServletRequest request) {
        return crudUserService.createStudent(new Usuario(user.username, user.password, "STUDENT"));
    }

    @PutMapping("/users/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassForm form, HttpServletRequest request) {
        return crudUserService.changePassword(form.username, form.oldPassword, form.newPassword);
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

    @DeleteMapping("/users/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id, HttpServletRequest request) {
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

