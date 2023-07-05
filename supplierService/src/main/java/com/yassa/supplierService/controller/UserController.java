/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.supplierService.controller;

/**
 *
 * @author MOHAMED BOULHAJ
 */
import com.yassa.supplierService.entities.Password;
import com.yassa.supplierService.entities.UserDTO;
import com.yassa.supplierService.service.UserService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping(path = "api/user")
public class UserController {

    UserService service;

    
    @PostMapping
    public boolean addUser(@RequestBody UserDTO userDTO){
          KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        Map<String, Object> customClaims = authentication.getAccount().getKeycloakSecurityContext()
                .getToken().getOtherClaims();
       return service.addUser(userDTO,customClaims.get("tenants").toString(),customClaims.get("company").toString());

    }
    
    @GetMapping(path = "/")
    public List<UserRepresentation> getUsers() {
        
         KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        Map<String, Object> customClaims = authentication.getAccount().getKeycloakSecurityContext()
                .getToken().getOtherClaims();
        List<UserRepresentation> users = service.getAllUsers(customClaims.get("tenants").toString());
        return users;
    }



    @GetMapping(path = "/{userId}")
    public UserDTO getUser(@PathVariable("userId") String userId) {
        UserDTO userDTO = service.getUser(userId);
//       UserDTO user = new UserDTO(userRepresentation.getUsername(), userRepresentation.getEmail(), "", userRepresentation.getFirstName(), userRepresentation.getLastName());

        return userDTO;
    }
    
    @GetMapping(path = "/get/{userId}")
    public UserDTO getUserInfo(@PathVariable("userId") String userId) {
        UserDTO userDTO = service.getUserInfo(userId);

        return userDTO;
    }

    @PutMapping(path = "/update/{userId}")
    public UserDTO updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
        return service.updateUser(userId, userDTO);
//        return "User Details Updated Successfully.";
    }
    
    @PutMapping(path = "/updateUser/{userId}")
    public UserDTO updateUserInfo(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
        return service.updateUserInfo(userId, userDTO);
//        return "User Details Updated Successfully.";
    }

    @PutMapping(path = "/update_pass/{userId}")
    public String updatepassword(@PathVariable("userId") String userId, @RequestBody Password password) {
        service.updatePassword(userId, password);
        return "User Details Updated Successfully.";
    }
}