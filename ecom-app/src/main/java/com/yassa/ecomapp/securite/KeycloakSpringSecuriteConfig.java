package com.yassa.ecomapp.securite;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakSpringSecuriteConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
//        http.authorizeRequests().antMatchers("/produits/**").authenticated();
http.authorizeRequests().antMatchers("/users/**").authenticated()
        .antMatchers("/user/**"). authenticated();

//http.authorizeRequests().antMatchers("/planning/**").authenticated();
//http.authorizeRequests().antMatchers("/planning").authenticated();
//       .antMatchers("/**").authenticated()
//               .antMatchers("/users/**").authenticated();
//         http.authorizeRequests().antMatchers("/users/**").authenticated();
        
        
    }



}