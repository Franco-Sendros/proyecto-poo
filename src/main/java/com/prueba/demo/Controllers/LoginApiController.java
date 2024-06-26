package com.prueba.demo.Controllers;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prueba.demo.Services.ServiceApiLogin;


import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;



import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
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
        public String userId;
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
    public ResponseEntity<?> login(@RequestBody RequestLogin request,HttpServletResponse responseCokies) {
        ResponseEntity<?> respuesta = serviceApiLogin.login(request, responseCokies);
        return respuesta;
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestBody RequestAuthorize request) {
        return serviceApiLogin.authorize(request);
    }

    
}
