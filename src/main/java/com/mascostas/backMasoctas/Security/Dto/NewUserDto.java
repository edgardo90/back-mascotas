/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//dto para el post de crear usuario
package com.mascostas.backMasoctas.Security.Dto;
//

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edgar
 */
@Getter
@Setter
public class NewUserDto {
    private String name;
    private String lastName;
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String district;
    private String direction;
    private String phone;
    private Set<String> roles = new HashSet<>();
}
