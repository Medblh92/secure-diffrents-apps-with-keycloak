/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.entites;
//import java.io.Serializable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
/**
 *
 * @author MOHAMED BOULHAJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginWrapper implements Serializable {

    private String grant_type;

    private String client_id;

    private String client_secret;

    private String username;
    private String password;

    public LoginWrapper(String username , String password){
        this.username = username;
        this.password = password;
        this.grant_type = "password";
        this.client_id = "products-app";
        this.client_secret = "xEOqBVN1qibthLHAE6X0mfDxpgISlRqR";
    }
    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("grant_type", this.grant_type);
        map.add("client_id", this.client_id);
        map.add("client_secret", this.client_secret);
        map.add("username", this.username);
        map.add("password", this.password);
        return map;
    }
}
