package com.yassa.supplierService.securite;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig  {
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

//    @Bean(name = "users")
//    public Keycloak usersResource() {
//        return KeycloakBuilder.builder()
//                .serverUrl(keycloakServerUrl)
//                .realm(realm)
//                .clientId(clientId)
//                .username(keycloakAdminName)
//                .password(keycloakAdminPassword)
//                .clientSecret(clientSecret)
//                .resteasyClient(new ResteasyClientBuilder()
//                        .connectionPoolSize(10)
//                        .build()
//                )
//                .build();
//
//    }


}
