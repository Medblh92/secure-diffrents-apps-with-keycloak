/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.supplierService.service;
import com.yassa.supplierService.entities.Credentials;
import com.yassa.supplierService.entities.UserDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author MOHAMED BOULHAJ
 */
@Service
@Log4j2
public class AdminService {

    @Autowired
    private RealmResource realmResource;

    @Autowired
//    @Qualifier("users")
    private Keycloak usersResource;

    public List<UserRepresentation> getAllUsers(String attributeValue) {
        String attributeName = "tenantId";
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> allUsers = usersResource.list();
        List<UserRepresentation> filteredUsers = allUsers.stream()
                .filter(user -> attributeValue.equals(getAttributeValue(user, attributeValue)))
                .collect(Collectors.toList());

//        return usersResource.list();
        return filteredUsers;
    }

    public boolean addUser(UserDTO user, String company) {

        UserRepresentation userRep = new UserRepresentation();
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(user.getPassword());
        userRep.setUsername(user.getUserName());
        userRep.setFirstName(user.getFirstname());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmailId());
        userRep.setCredentials(Collections.singletonList(credential));
        userRep.setEnabled(true);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("tenantId", Collections.singletonList("tenant-" + company));
        attributes.put("company", Collections.singletonList(company));
        userRep.setAttributes(attributes);

        Response res = realmResource.users().create(userRep);
        String createdUserId = null;
        if (res.getStatus() == 201) {
            // User created successfully
            createdUserId = res.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            System.out.println("Created user ID: " + createdUserId);
        } else {
            // User creation failed
            System.out.println("Failed to create user");
        }
        String roleName = "superadmin"; // Replace with the actual role name

// Find the role by name and retrieve its ID
        RoleRepresentation roleId = null;
        List<RoleRepresentation> roles = realmResource.roles().list();
         System.out.println(roles.toString());
        for (RoleRepresentation role : roles) {
            if (roleName.equals(role.getName())) {
                roleId = role;
                System.out.println(role.getId());
                break;
            }
        }
System.out.println("sqq32" + roleId);
// Assign the role to the user
        if (roleId != null) {
           
//            realmResource.users().get(createdUserId).roles().realmLevel().listAll().add(0, roleRepresentation);
//             realmResource.users().get(createdUserId).roles().realmLevel().listAll().add(roleId);
             realmResource.users().get(createdUserId).roles().realmLevel().add(Arrays.asList(roleId));
             
             
        } else {
            // Role not found
            System.out.println("Role not found: " + roleName);
        }
        if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
            System.out.println(res.getStatus());
            return true;
        }
        return false;

    }

    private String getAttributeValue(UserRepresentation user, String attributeName) {
//        List<String> attributeValues = user.getAttributes().get(attributeName);
//        return (attributeValues != null && !attributeValues.isEmpty()) ? attributeValues.get(0) : null;
        List<RoleRepresentation> roles = realmResource.users().get(user.getId()).roles().realmLevel().listAll();

        // Print the user and their roles
        System.out.println("User: " + user.getUsername());
        String searchedName = null;
        for (RoleRepresentation role : roles) {
//            return (role.getName().equals(attributeName)) ? role.getName() : null;
            if(role.getName().equals(attributeName)){
                searchedName = role.getName();
                break;
            }
        }
        return searchedName;
    }
}
