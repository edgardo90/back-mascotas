/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Security.Service;
//

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//importo lo que cree
import com.mascostas.backMasoctas.Security.Model.UserModel;// este es el modelo Usuario que cree
import com.mascostas.backMasoctas.Security.Model.PrimaryUser;// este es el UsuarioPrincpal que cree

/**
 *
 * @author edgar
 */
@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userService.getByUserName(username).get();
        return PrimaryUser.build(user);
    }
}
