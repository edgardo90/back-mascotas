/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//dto para el login del post login
package com.mascostas.backMasoctas.Security.Dto;
//
import javax.validation.constraints.NotBlank; // con esto hago que no puede enviar info de strin vacio
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author edgar
 */

@Getter @Setter
public class LoginUserDto {
    @NotBlank // con esto hago que no puede enviar info de strin vacio
    private String userName;
    @NotBlank
    private String password; 
}
