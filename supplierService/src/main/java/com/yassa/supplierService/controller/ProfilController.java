/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.supplierService.controller;
import com.yassa.supplierService.entities.Password;
import com.yassa.supplierService.entities.UserDTO;
import com.yassa.supplierService.service.UserService;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author MOHAMED BOULHAJ
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "api/profil")
public class ProfilController {
    UserService service;
    
     @GetMapping(path = "/")
    public UserDTO getUser() {
         KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        String userId = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken().getSubject();
        UserDTO userDTO = service.getUser(userId);
        return userDTO;
    }
      @PutMapping(path = "/update")
    public UserDTO updateUser( @RequestBody UserDTO userDTO) {
         KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        String userId = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken().getSubject();
        return service.updateUser(userId, userDTO);

    }
     @PutMapping(path = "/update_pass")
    public String updatepassword( @RequestBody Password password) {
         KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        System.out.println(password);
        String userId = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken().getSubject();
        service.updatePassword(userId, password);
        return "User Details Updated Successfully.";
    }
}
