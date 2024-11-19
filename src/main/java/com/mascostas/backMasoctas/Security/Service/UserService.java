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
import java.util.List;
//importo lo que cree
import com.mascostas.backMasoctas.Security.Model.UserModel;// este es el modelo Usuario que cree
import com.mascostas.backMasoctas.Security.Repository.iUserRepository; // traigo mi repository que cree

/**
 *
 * @author edgar
 */

@Service
@Transactional // evita errores que se cargue en la base de datos(eso creo)
public class UserService {
     @Autowired
    iUserRepository iuserRepository ; // el repository que cree lo implemento aca
    
    public Optional<UserModel> getByUserName(String userName){ // trae un usuario por su username
        return iuserRepository.findByUserName(userName);
    }
    
    public boolean existsByUserName(String userName){ // me devuelve true si existe ese userName
        return iuserRepository.existsByUserName(userName);
    }
    
    public boolean existsByEmail(String email){ // me devuelve true si existe ese email
        return iuserRepository.existsByEmail(email);
    }
    
    public void save(UserModel user){ // crea un usuario en la base de datos
        iuserRepository.save(user);
    }
    
    //funciones  que cree por mi cuenta(funciones echas por mi)
    public List<UserModel> getUsers(){ // me trae todos los usuarios
        List<UserModel> users = iuserRepository.findAll();
        return users;
    }
    
    public  void deletedUser(int id){ // elemino un usuario por su id
        iuserRepository.deleteById(id);
    }
    
    public UserModel  findUser(int id){
        UserModel user = iuserRepository.findById(id).orElse(null);
        return user;
    }
}
