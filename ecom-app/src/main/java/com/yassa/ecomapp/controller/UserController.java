/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.controller;

import com.yassa.ecomapp.service.KeycloakUserService;
import java.util.Collections;
import java.util.HashMap;
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
    
    @GetMapping("/addUser")
    public String addUser(HttpServletRequest request,HttpServletResponse response,Model model){
      // Create a new UserRepresentation with the desired user attributes
      // Create a map for custom attributes
Map<String, List<String>> attributes = new HashMap<>();
attributes.put("tenantId", Collections.singletonList("ecom-realm"));

        UserRepresentation user = new UserRepresentation();
        user.setUsername("john.doe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setAttributes(attributes);
keycloakUserService.addUser(user);
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
