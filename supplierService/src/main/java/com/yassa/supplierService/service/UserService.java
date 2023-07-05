/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.supplierService.service;

/**
 *
 * @author MOHAMED BOULHAJ
 */
import java.util.Collections;
import java.util.List;
import javax.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.yassa.supplierService.entities.Credentials;
import com.yassa.supplierService.entities.Password;
import com.yassa.supplierService.entities.UserDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *
 * @author hcnjob
 */
@Service
@Log4j2
public class UserService {

    @Autowired
    private RealmResource realmResource;

    @Autowired
//    @Qualifier("users")
    private Keycloak usersResource;

    public List<UserRepresentation> getAllUsers(String attributeValue ) {
        String attributeName="tenantId";
        UsersResource usersResource = realmResource.users();
         List<UserRepresentation> allUsers = usersResource.list();
        List<UserRepresentation> filteredUsers = allUsers.stream()
                .filter(user -> attributeValue.equals(getAttributeValue(user, attributeName)))
                .collect(Collectors.toList());
        
//        return usersResource.list();
return filteredUsers;
    }


    public boolean addUser(UserDTO user,String tenant,String company) {


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
        if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return true;
        }
        return false;

    }

    public UserDTO getUser(String userId) {
        UserDTO userDTO = new UserDTO();
//        UserRepresentation userRep = realmResource.users().search(userId).get(0);
        UserResource userRes = realmResource.users().get(userId);
        log.error("foo23");

        UserRepresentation userRep = userRes.toRepresentation();

        userDTO.setEmailId(userRep.getEmail());
        log.error(userDTO.getEmailId());
        userDTO.setFirstname(userRep.getFirstName());
        userDTO.setLastName(userRep.getLastName());
        userDTO.setUserName(userRep.getUsername());
        return userDTO;
    }
        public UserDTO getUserInfo(String userId) {
        UserDTO userDTO = new UserDTO();

        UserResource userRes = realmResource.users().get(userId);
        log.error("foo23");

        UserRepresentation userRep = userRes.toRepresentation();

        userDTO.setEmailId(userRep.getEmail());
        log.error(userDTO.getEmailId());
        userDTO.setFirstname(userRep.getFirstName());
        userDTO.setLastName(userRep.getLastName());
        userDTO.setUserName(userRep.getUsername());
        return userDTO;
    }
    public UserDTO updateUser(String userId, UserDTO userDTO) {
//        CredentialRepresentation credential = Credentials
//                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
//        user.setCredentials(Collections.singletonList(credential));
        realmResource.users().get(userId).update(user);
        return userDTO;
    }
    public UserDTO updateUserInfo(String userId, UserDTO userDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        realmResource.users().get(userId).update(user);
        return userDTO;
    }
    public void updatePassword(String userId, Password password) {
//        CredentialRepresentation credential = Credentials
//                .createPasswordCredentials(password);
//        UserRepresentation user = new UserRepresentation();
//        user.setCredentials(Collections.singletonList(credential));
//        realmResource.users().get(userId).update(user);
CredentialRepresentation newPassword = new CredentialRepresentation();
newPassword.setType(CredentialRepresentation.PASSWORD);
newPassword.setValue(password.getPassword());
newPassword.setTemporary(false);
realmResource.users().get(userId).resetPassword(newPassword);
    }

    public void resetPassword(String email, String password, String newPasssword) {

    }
    
    private String getAttributeValue(UserRepresentation user, String attributeName) {
        List<String> attributeValues = user.getAttributes().get(attributeName);
        return (attributeValues != null && !attributeValues.isEmpty()) ? attributeValues.get(0) : null;
    }
}