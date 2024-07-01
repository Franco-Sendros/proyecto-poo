package com.prueba.demo.Services;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.prueba.demo.Controllers.LoginApiController;
import com.prueba.demo.Controllers.LoginApiController.RequestAuthorize;
import com.prueba.demo.Controllers.LoginApiController.RequestLogin;
import com.prueba.demo.Controllers.LoginApiController.ResponseAuthorize;
import com.prueba.demo.Models.Permiso;
import com.prueba.demo.Models.Token;
import com.prueba.demo.Models.Usuario;
import com.prueba.demo.Repositories.PermisosRepository;
import com.prueba.demo.Repositories.SistemaRepository;
import com.prueba.demo.Repositories.TokenRepository;
import com.prueba.demo.Repositories.UsuarioRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class ServiceApiLogin {

    // Llave secreta para firmar el token
    private final String SECRET_KEY = "mySecretKey";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SistemaRepository sistemaRepository;

    @Autowired
    private PermisosRepository permisosRepository;



    
    public ResponseEntity<?> login(RequestLogin request, HttpServletResponse responseCokies){
        Usuario usuario = usuarioRepository.findByName(request.username);
        if (!usuario.getName().equals(request.username)) {
            usuario = null;
        }
        if (usuario != null && usuario.getPassword().equals(request.password)) {
            Long userId = usuario.getId();

            // Generar el token
            long expiresIn = 3600; // Expira en 1 hora (3600 segundos)
            String token = Jwts.builder()
                    .setSubject(request.username)
                    .claim("userId", userId)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();

            // Guardar el token en la base de datos
            Token tokenEntity = new Token();
            tokenEntity.setToken(token);
            tokenEntity.setUsuario(usuario);
            tokenEntity.setCreatedAt(new Date());
            tokenEntity.setExpiresAt(new Date(System.currentTimeMillis() + expiresIn * 1000));
            tokenRepository.save(tokenEntity);

            // Crear la cookie con el token
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600); // 1 hora
            cookie.setPath("/"); // Hacer que la cookie esté disponible en toda la aplicación
            responseCokies.addCookie(cookie); 
            
            // Crear la respuesta
            LoginApiController.ResponseLogin response = new LoginApiController.ResponseLogin();
            response.token = token;
            response.userId = userId+"";
            response.expiresIn = expiresIn;
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } else {
            // Credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

    }


    public ResponseEntity<?> authorize(RequestAuthorize request) {
        ResponseAuthorize response = new ResponseAuthorize();
     
        // Buscar el token en la base de datos
        Token token = tokenRepository.findByToken(request.token);
        if (token == null || token.getExpiresAt().before(new Date())) {
            // El token no existe, no autorizado
            response.authorized = false;
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        }

        // Verificar si el sistema asociado al token no es válido
        if (sistemaRepository.findBySystemName(request.systemId) == null) {
            // El usuario no existe, no autorizado
            response.authorized = false;
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        
        
        // Verificar si el token está asociado al sistema
        Usuario usuarioDeToken = token.getUsuario();
        
        sistemaRepository.findBySystemName(request.systemId);//obtuve el sistema que si exite
        Set<Permiso> permisosArray = permisosRepository.findByUsuario(usuarioDeToken);
        for (Permiso p : permisosArray) {
            // Si pasó todas las verificaciones, autorizado
            if (p.getSistema().getSystemName().equals(request.systemId)) {
                response.authorized = true;
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }

        // Si el usuario no tiene permiso a ese sistema pues no tiene jaja 
        response.authorized = false;
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    public boolean internalAuthorize(String tokenValue, String systemId) {
        // Crear una instancia de RequestAuthorize y asignar los valores de token y systemId
        RequestAuthorize request = new RequestAuthorize();
        request.token = tokenValue;
        request.systemId = systemId;
    
        // Llamar al método authorize con la solicitud creada y obtiene la respuesta
        ResponseEntity<?> response = authorize(request);
    
        // Verificar si el estado de la respuesta es HttpStatus.OK (200 OK)
        if (response.getStatusCode() == HttpStatus.OK) {
            // Obtener el cuerpo de la respuesta y convertirlo a un objeto ResponseAuthorize
            ResponseAuthorize responseAuthorize = (ResponseAuthorize) response.getBody();
    
            // Verificar si responseAuthorize no es nulo y si está autorizado
            return responseAuthorize != null && responseAuthorize.authorized;
        }
    
        // Si el estado de la respuesta no es HttpStatus.OK, devolver false
        return false;
    }
}
