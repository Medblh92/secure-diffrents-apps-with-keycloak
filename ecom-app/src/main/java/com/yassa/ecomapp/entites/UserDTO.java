/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.ecomapp.entites;

/**
 *
 * @author MOHAMED BOULHAJ
 */
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO implements Serializable {

    private String userName;
    private String emailId;
    private String password;
    private String firstname;
    private String lastName;

    public UserDTO(String emailId, String userName, String firstname, String lastName) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.emailId = emailId;
        this.userName = userName;
        this.password = "";
    }



}
