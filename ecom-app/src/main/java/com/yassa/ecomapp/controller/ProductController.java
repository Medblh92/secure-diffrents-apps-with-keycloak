package com.yassa.ecomapp.controller;

import com.yassa.ecomapp.entites.ProductRepository;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.facade.SimpleHttpFacade;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    
   

    @GetMapping("/")
    public String index() {


        return "index";
    }


    @GetMapping("/produits")
    public String products(HttpServletRequest request,
            HttpServletResponse response, @RequestParam(name="name", required=false, defaultValue="amar") String name, Model model) {
           KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
            .getContext().getAuthentication();
        Map<String, Object> customClaims = authentication.getAccount().getKeycloakSecurityContext()
            .getToken().getOtherClaims();
        System.out.println(customClaims.get("tenants"));
        model.addAttribute("tenant", customClaims.get("tenants"));
        model.addAttribute("name", name);
        model.addAttribute("products", productRepository.findAll());

        return "products";
    }




    @RequestMapping("/hello")
    @ResponseBody
    String hello() {
        return "Hello World!";
    }


    @GetMapping("/home")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="amar") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }




}
