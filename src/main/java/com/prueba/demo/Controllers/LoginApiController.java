package com.prueba.demo.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prueba.demo.Services.ServiceApiLogin;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/sistema_login")
public class LoginApiController {

    @Autowired
    private ServiceApiLogin serviceApiLogin;

    // Clase para representar la solicitud de autenticación
    static public class RequestLogin {
        public String username;
        public String password;
    }

    // Clase para representar la respuesta de autenticación
    static public class ResponseLogin {
        public String token;
        public Long userId;
        public long expiresIn;
    }

    // Clase para representar la solicitud de autenticación
    static public class RequestAuthorize {
        public String token;
        public String systemId;
    }

    // Clase para representar la respuesta de autenticación
    static public class ResponseAuthorize {
        public boolean authorized;
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin request) {
        return serviceApiLogin.login(request);
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestBody RequestAuthorize request) {
        return serviceApiLogin.authorize(request);
    }
    
}
