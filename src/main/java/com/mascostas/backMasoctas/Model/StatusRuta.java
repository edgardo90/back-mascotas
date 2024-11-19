/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Model;
//

import lombok.Getter; // esto lo importo
import lombok.Setter; // esto lo importo

/**
 *
 * @author edgar
 */

@Getter @Setter
public class StatusRuta {

    private String message;

    public StatusRuta() {
    }

    public StatusRuta(String message) {
        this.message = message;
    }
}
