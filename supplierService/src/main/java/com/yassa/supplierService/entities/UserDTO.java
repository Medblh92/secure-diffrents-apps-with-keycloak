/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.yassa.supplierService.entities;

/**
 *
 * @author MOHAMED BOULHAJ
 */
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO implements Serializable  {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private String userName;
    private String emailId;
    private String password;
    private String firstname;
    private String lastName;
}
