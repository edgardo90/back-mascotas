/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Dto;
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
public class PublicationDto {
    private String name;
    private String type;
    private String race;
    private String sex;
    private String sizePet;
    private String image;
    private String description;
    private String district;
    private String direction;
    private String dateLost;
    private Boolean isLost;
    private int userId;
}
