/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.supplierService.controller;
import com.yassa.supplierService.entities.UserDTO;
import com.yassa.supplierService.service.AdminService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author MOHAMED BOULHAJ
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "api/admin")
public class AdminController {
    
    AdminService service;

    
     @GetMapping(path = "/users")
    public List<UserRepresentation> getUsers() {
        List<UserRepresentation> users = service.getAllUsers("superadmin");
        return users;
    }
    
     @PostMapping(path = "/user/{company}")
    public boolean addUser(@PathVariable("company") String company,@RequestBody UserDTO userDTO){
        
       return service.addUser(userDTO,company);

    }
    
}
