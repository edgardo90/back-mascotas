/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Model;
//

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.persistence.*;// importo todo lo de persistence
import lombok.Getter;
import lombok.Setter;
//
import com.mascostas.backMasoctas.Security.Model.UserModel;

/**
 *
 * @author edgar
 */
@Getter
@Setter // esto sirve para usar .get o el .set del model que cree
@Entity
@Table(name = "publications") // es el nombre que le pongo a mi tabla
public class PublicationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Se guarda como user_id
    @JsonIgnoreProperties({"publications"}) // Evita la recursión pero muestra al usuario
    private UserModel user;

    public PublicationModel() {
    }

    public PublicationModel(
            String name,
            String type,
            String race,
            String sex,
            String sizePet,
            String image,
            String description,
            String district,
            String direction,
            String dateLost,
            Boolean isLost,
            UserModel user // Usuario al que pertenece la publicación
    ) {
        this.name = name;
        this.type = type;
        this.race = race;
        this.sex = sex;
        this.sizePet = sizePet;
        this.image = image;
        this.description = description;
        this.district = district;
        this.direction = direction;
        this.dateLost = dateLost;
        this.isLost = isLost;
        this.user = user;
    }

}
