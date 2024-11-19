/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Service;
//

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;//
import java.util.List;
//traigo lo que cree
import com.mascostas.backMasoctas.Model.PublicationModel; // importo el modelo que cree 
import com.mascostas.backMasoctas.Repository.PublicationRepository; // traigo mi repository que cree

/**
 *
 * @author edgar
 */
@Service
public class PublicationService implements IPublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public List<PublicationModel> getPublications() { // traigo todas las tasks
        List<PublicationModel> listPublication = publicationRepository.findAll();
        return listPublication;
    }

    @Override
    public void savePublication(PublicationModel publication) {
        publicationRepository.save(publication);
    }

    @Override
    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }

    @Override
    public PublicationModel findPublication(Long id) {
        PublicationModel publicationModel = publicationRepository.findById(id).orElse(null);
        return publicationModel ;
    }

}
