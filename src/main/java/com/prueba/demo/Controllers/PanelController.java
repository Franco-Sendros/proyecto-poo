package com.prueba.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.demo.Services.PanelService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/sistema_login/panelcontroller")
public class PanelController {

    @Autowired
    private PanelService panelService;

    @GetMapping("")
    public String getPaginaPrincipal(HttpServletRequest requestCookies, Model model,HttpServletResponse responseCookies) {
        boolean sessionOK = panelService.checkSession(requestCookies);
        if (!sessionOK) { 
            //guarda la ultima sesion
            Cookie galleta = new Cookie("ultimaPagina", requestCookies.getRequestURL().toString());
            responseCookies.addCookie(galleta);
            // Redirige al usuario a la página de login si la sesión no es válida
            return "redirect:/sistema_login/panelcontroller/login";
        }
        // Si la sesión es válida, simplemente muestra la página principal
        return "interfaz";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/redirection")
    public String redireccionamiento(HttpServletRequest request, HttpServletResponse response) {
        // Busca la cookie "ultimaPagina" en las cookies de la solicitud
        Cookie[] cookies = request.getCookies();
        if (cookies!= null) {
            for (Cookie cookie : cookies) {
                if ("ultimaPagina".equals(cookie.getName())) {
                    // Encuentra la cookie "ultimaPagina" y obtiene su valor
                    String ultimaPagina = cookie.getValue();
                    
                    // Construye la URL de redirección
                    System.out.println("Ultima pagina: " + ultimaPagina);
                    String redirectUrl = "redirect:" + ultimaPagina;
                    
                    // Redirige al usuario a la URL de la última página visitada
                    return redirectUrl;
                }
            }
        }
        
        // Si no se encuentra la cookie, puedes decidir qué hacer, por ejemplo, redirigir a una página predeterminada
        return "redirect:/paginaPredeterminada";
    }
    
    
}

