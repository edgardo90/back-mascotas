/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//creo una new Java Interface(esto no es una clase) , primero creo la interface para el service
package com.mascostas.backMasoctas.Service;
//

import java.util.List;
import com.mascostas.backMasoctas.Model.PublicationModel;

/**
 *
 * @author edgar
 */
public interface IPublicationService {

    public List<PublicationModel> getPublications(); // trae las publications

    public void savePublication(PublicationModel publication); // crea la publication

    public void deletePublication(Long id); // elemina la publication

    public PublicationModel findPublication(Long id); // busca la publication por su id
}
