/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//para ser esto cree una Class, esto es es mi modelo Rol con cual se va crear en la tabla de data base 
package com.mascostas.backMasoctas.Security.Model;
//
import com.mascostas.backMasoctas.Security.Enum.RolName;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "rols") // es el nombre que le pongo a mi tabla
public class RolModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RolName rolName;

    //Constructor
    public RolModel() {
    }

    public RolModel(RolName rolName) {
        this.rolName = rolName;
    }
}
