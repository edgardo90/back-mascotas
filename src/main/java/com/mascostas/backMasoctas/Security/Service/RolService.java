/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Security.Service;
//

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//importo lo que cree
import com.mascostas.backMasoctas.Security.Model.RolModel;
import com.mascostas.backMasoctas.Security.Enum.RolName;
import com.mascostas.backMasoctas.Security.Repository.iRolRepository;

/**
 *
 * @author edgar
 */
@Service
@Transactional
public class RolService {
    @Autowired
    iRolRepository irolRepository;

    public Optional<RolModel> getByRolNombre(RolName rolName) {
        return irolRepository.findByRolName(rolName);
    }

    public void save(RolModel rol) {
        irolRepository.save(rol);
    }
}
