/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.controller;

import com.yassa.ecomapp.entites.UserDTO;
import com.yassa.ecomapp.service.KeycloakUserService;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author devw5
 */
@Controller
@Log4j2
public class UserController implements ErrorController {
     @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            modelAndView.setViewName("errors");
        } else if (response.getStatus() == HttpStatus.FORBIDDEN.value()) {
            modelAndView.setViewName("errors");
        } else if (response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            modelAndView.setViewName("errors");
        } else {
            modelAndView.setViewName("errors");
        }

        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/")
    public String index() {
        try {
            KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder
                    .getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                return "index";
            } else {
                return "redirect:/sso/login";
            }
        } catch (Exception e) {
            return "redirect:/sso/login";
        }
    }

    @GetMapping("/users")
    public String users(HttpServletRequest request, HttpServletResponse response, Model model) {

        ResponseEntity<List<UserRepresentation>> users = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/user/",
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

        return "users";
    }

    @GetMapping("/user")
    public String getUser() {
        return "add_user";
    }

    @PostMapping("/user")
    public String addUser(
            @RequestParam String userName,
            @RequestParam String emailId,
            @RequestParam String firstname,
            @RequestParam String lastName,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
//      Add user
        UserDTO userTDO = new UserDTO(userName, emailId, password, firstname, lastName);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(userTDO);
        ResponseEntity<Boolean> respose = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/user/",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Boolean>() {
        });

        model.addAttribute("is_user_added", respose.getBody());
//      Get users list
        ResponseEntity<List<UserRepresentation>> users = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/user/",
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

        return "users_list";
//        return "add_user";
    }

    @GetMapping("/user/profil")
    public String getUser(HttpServletRequest request, Model model) {
//        String userId = request.getUserPrincipal().getName();

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        String userId = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken().getSubject();

        ResponseEntity<UserDTO> respose = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/user/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<UserDTO>() {
        });
        log.error("foo89");
        log.error(respose.getBody());
        model.addAttribute("profil_data", respose.getBody());
        model.addAttribute("is_update", false);

        return "profil";
    }

    @PostMapping(value = "/user/profil")
//    public  String updateProfil(@RequestBody UserDTO userTDO,HttpServletRequest request,  Model model) {
    public String updateProfil(
            @RequestParam String userName,
            @RequestParam String emailId,
            @RequestParam String firstname,
            @RequestParam String lastName,
            HttpServletRequest request,
            Model model) {
        log.error("updateProfil1");
//        log.error(userTDO.toString());
        UserDTO userTDO = new UserDTO(emailId, userName, firstname, lastName);

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        String userId = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken().getSubject();

        HttpEntity<UserDTO> requestEntity = new HttpEntity(userTDO);

        ResponseEntity<UserDTO> response = keycloakRestTemplate.exchange(
                "http://localhost:8083/api/user/update/" + userId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<UserDTO>() {
        }
        );
        model.addAttribute("profil_data", response.getBody());
        model.addAttribute("is_update", true);
        return "profil";
    }

    
}
