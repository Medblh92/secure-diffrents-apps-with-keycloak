/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.controller;

import com.yassa.ecomapp.service.KeycloakUserService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author devw5
 */
@Controller
public class UserController {
    @Autowired
    private  KeycloakUserService keycloakUserService;

  
    @GetMapping("/users")
    public String users(HttpServletRequest request,HttpServletResponse response,Model model){
        
     List<UserRepresentation> users=  keycloakUserService.getAllUsers();
     KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
            .getContext().getAuthentication();
     Map<String, Object> customClaims = authentication.getAccount().getKeycloakSecurityContext()
            .getToken().getOtherClaims();
        System.out.println(customClaims.get("tenants"));
        model.addAttribute("tenant", customClaims.get("tenants"));
        model.addAttribute("users", users);
     
        return "users";
    }
    
    
    
}
