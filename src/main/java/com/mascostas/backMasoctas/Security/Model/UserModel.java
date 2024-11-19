/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//para ser esto cree una Class, esto es es mi modelo Rol con cual se va crear en la tabla de data base 
package com.mascostas.backMasoctas.Security.Model;
//

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mascostas.backMasoctas.Model.PublicationModel;
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

/**
 *
 * @author edgar
 */
@Getter
@Setter // esto sirve para usar .get o el .set del model que cree
@Entity
@Table(name = "users") // es el nombre que le pongo a mi tabla
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora los proxies de Hibernate
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;
    @NotNull
    @Column(unique = true)
    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String district;
    private String direction;
    private String phone;

    //esto es una prueba de relacion con la tabla RolModel
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id")) // aca hice la tabla de relacion con roles
    private Set<RolModel> roles = new HashSet<>();
    
    //Define la relación de Uno a Muchos: un usuario puede tener muchas publicaciones.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Este lado se serializa normalmente
    private Set<PublicationModel> publications = new HashSet<>();

    public UserModel() {
    }

    public UserModel(String name, String lastName, String userName, String email, String password, String district, String direction, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.district = district;
        this.direction = direction;
        this.phone = phone;
    }

    public Set<RolModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolModel> roles) {
        this.roles = roles;
    }
}
