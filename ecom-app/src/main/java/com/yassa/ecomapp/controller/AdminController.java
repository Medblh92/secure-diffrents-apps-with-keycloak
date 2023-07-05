/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.controller;
import com.yassa.ecomapp.entites.UserDTO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *
 * @author MOHAMED BOULHAJ
 */
@Controller
@Log4j2
@RequestMapping("admin")
public class AdminController {
    
    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;
    
    @GetMapping("/users")
    public String users(HttpServletRequest request, HttpServletResponse response, Model model) {

        ResponseEntity<List<UserRepresentation>> users = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/admin/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserRepresentation>>() {
        });
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        Map<String, Object> customClaims = authentication.getAccount().getKeycloakSecurityContext()
                .getToken().getOtherClaims();
        System.out.println(users.getBody());
        model.addAttribute("tenant", customClaims.get("tenants"));
        model.addAttribute("users", users.getBody());

        return "super-admins";
    }
    
    @PostMapping("/user")
    public String addUser(
            @RequestParam String userName,
            @RequestParam String emailId,
            @RequestParam String firstname,
            @RequestParam String lastName,
            @RequestParam String password,
            @RequestParam String company,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
//      Add user
        UserDTO userTDO = new UserDTO(userName, emailId, password, firstname, lastName);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(userTDO);
        System.out.println(company);
        ResponseEntity<Boolean> respose = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/admin/user/" + company,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Boolean>() {
        });

        model.addAttribute("is_user_added", respose.getBody());
//      Get users list
        ResponseEntity<List<UserRepresentation>> users = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/admin/users/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserRepresentation>>() {
        });
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        Map<String, Object> customClaims = authentication.getAccount().getKeycloakSecurityContext()
                .getToken().getOtherClaims();
        System.out.println(users.getBody());
        model.addAttribute("tenant", customClaims.get("tenants"));
        model.addAttribute("users", users.getBody());

        return "super-admin-list";
//        return "add_user";
    }
    
}
