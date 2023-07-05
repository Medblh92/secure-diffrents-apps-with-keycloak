package com.yassa.ecomapp.securite;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class KeycloakConfig {
    
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;
    
     @Value("${keycloak.credentials.secret}")
    private String clientSecret;
     
     @Value("${admin_username}")
    private String keycloakAdminName;
     
      @Value("${admin_password}")
    private String keycloakAdminPassword;
    
    
    
    @Bean
    KeycloakSpringBootConfigResolver configResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    KeycloakRestTemplate keycloakRestTemplate(KeycloakClientRequestFactory keycloakClientRequestFactory) {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);

    }
    
    @Bean
    RestTemplate restTemplate(){
    final RestTemplate restTemplate = new RestTemplate();
   return restTemplate;
    }
    
    
    
    
     @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(realm);
    }
     @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
               .serverUrl(keycloakServerUrl)
               .realm(realm)
               .clientId(clientId)
               .username(keycloakAdminName)
               .password(keycloakAdminPassword)
               .clientSecret(clientSecret)
                .build();
   }
    
   

   
}