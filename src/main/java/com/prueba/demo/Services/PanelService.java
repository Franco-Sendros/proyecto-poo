package com.prueba.demo.Services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.servlet.http.Cookie;


@Service
@Transactional
public class PanelService { 
    
    @Autowired
    private ServiceApiLogin serviceApiLogin;

    public boolean checkSession(HttpServletRequest requestCookies) {
    Cookie[] cookies = requestCookies.getCookies();
    String tokenValue = null;
        System.out.println("Buscando cookies");
    if (cookies!= null) {
        System.out.println("Hay Cookies");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                System.out.println("Se encontro la cookie");
                tokenValue = cookie.getValue();
                break;
            }
        }
    }
    System.out.println("Comprobando si funciona");
        if (tokenValue!= null) {
            System.out.println(serviceApiLogin.internalAuthorize(tokenValue, "PANEL_USUARIOS"));
            return serviceApiLogin.internalAuthorize(tokenValue, "PANEL_USUARIOS");
        } else {
        
        return false; // Redirige al usuario a la página de login
        }
    }

}
