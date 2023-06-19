/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.service;

/**
 *
 * @author devw5
 */

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;


@Service
public class KeycloakUserService {

    
    @Autowired
    private  RealmResource realmResource;
    
   
    

    public List<UserRepresentation> getAllUsers() {
        
        UsersResource usersResource = realmResource.users();
        return usersResource.list();
    }
}
