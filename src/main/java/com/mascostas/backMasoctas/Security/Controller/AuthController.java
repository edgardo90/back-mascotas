/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//aca van estar mis rutas para el usurio y para el login
package com.mascostas.backMasoctas.Security.Controller;
//importo lo que cree

import com.mascostas.backMasoctas.Security.Dto.JwtDto;
import com.mascostas.backMasoctas.Security.Dto.LoginUserDto;
import com.mascostas.backMasoctas.Security.Dto.NewUserDto;
import com.mascostas.backMasoctas.Security.Model.RolModel;
import com.mascostas.backMasoctas.Security.Model.UserModel;
import com.mascostas.backMasoctas.Security.Enum.RolName;
import com.mascostas.backMasoctas.Security.Service.RolService;
import com.mascostas.backMasoctas.Security.Service.UserService;
import com.mascostas.backMasoctas.Security.jwt.JwtProvider;
import com.mascostas.backMasoctas.Model.StatusRuta;
//
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;//configura los coors
import org.springframework.web.bind.annotation.RequestMethod;// sirve para configurar los coors
import org.springframework.web.bind.annotation.GetMapping; // esto lo importo yo , sirve para los gets
import org.springframework.web.bind.annotation.PathVariable; // esto para que sea por params
import org.springframework.web.bind.annotation.RequestParam; // esto sirve para venir por query
import org.springframework.web.bind.annotation.RestController; // esto lo importo yo
import org.springframework.http.HttpStatus; // le doy el status si esta ok (creo que tambien si esta todo mal)
import org.springframework.http.ResponseEntity; //para enviar datos o un mensaje en formato json
import org.springframework.web.bind.annotation.PostMapping;//ruta para el post
import org.springframework.web.bind.annotation.RequestBody; // esto sirve para traer las variables del post
import org.springframework.web.bind.annotation.DeleteMapping; // ruta para el deleted
import org.springframework.web.bind.annotation.PutMapping; // ruta para el put
import org.springframework.beans.factory.annotation.Autowired;//// para el service , en este caso ITaskService
import java.util.List; //
import java.util.ArrayList;// esto sirve para crear un array
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author edgar
 */
@RestController
@RequestMapping("/auth") // con esto todas las rutas de abajo empieza con /auth
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/create") // creo un nuevo usuario
    public ResponseEntity<?> creteUser(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new StatusRuta("Campos mal puestos , uno o mas esta vacio"), HttpStatus.NOT_IMPLEMENTED);
        }
        if (userService.existsByUserName(newUserDto.getUserName())) { // si da true que exite ese usuario va tirar ese error
            return new ResponseEntity(new StatusRuta("Ese nombre de usuario ya existe"), HttpStatus.NOT_IMPLEMENTED);
        }
        if (userService.existsByEmail(newUserDto.getEmail())) { // si da true que existe ese email va dar el siguiente error
            return new ResponseEntity(new StatusRuta("Ese email ya existe"), HttpStatus.NOT_IMPLEMENTED);
        }
        UserModel user = new UserModel(newUserDto.getName(), newUserDto.getLastName(), newUserDto.getUserName(), // creo un usario utilizando el dto de NuevoUsuario
                newUserDto.getEmail(), passwordEncoder.encode(newUserDto.getPassword()), newUserDto.getDistrict(), newUserDto.getDirection(), newUserDto.getPhone()); // en password... lo que hace encriptar la contraseña
        Set<RolModel> roles = new HashSet<>(); // creo un nuevo set con modelo Rol variable "roles"
        roles.add(rolService.getByRolNombre(RolName.ROLE_USER).get()); // por defecto agrego el rol de usuario
        if (newUserDto.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolName.ROLE_ADMIN).get());
        }
        user.setRoles(roles); //agrego el rol o roles
        userService.save(user); // guardo el usario creado
        Map<String, Object> response = new HashMap<>(); // es una estructura de datos que almacena pares clave-valo
        response.put("message", "Usuario guardado"); //agrego una key y su valor  la key del object  va ser  "message" y "Usuario guardado" su valor
        response.put("data", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")// se logea el usuario
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StatusRuta status = new StatusRuta("Campos mal puestos");
            return new ResponseEntity(status, HttpStatus.BAD_REQUEST);
        }
        if (!userService.existsByUserName(loginUserDto.getUserName())) {
            StatusRuta status = new StatusRuta("No se encunetra ese usuario en la base de datos");
            return new ResponseEntity(status, HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserDto.getUserName(), loginUserDto.getPassword()));
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    //rutas creadas por mi
//    @PreAuthorize("hasRole('ADMIN')") // con esto solo van poder ingresar esta ruta si esta logueado y tiene como rol "ADMIN"
    @GetMapping("/user/all") // traigo todos los usuarios
    public List<UserModel> getUsers() {
        List<UserModel> users = userService.getUsers();
        return users;
    }

    @GetMapping("/user/{userName}") // traigo un usuario por su username
    public ResponseEntity<Object> getUserByUserName(@PathVariable String userName) {
        Optional<UserModel> user = userService.getByUserName(userName);
        if (user == null) {
            StatusRuta status = new StatusRuta("No se encontro el usuario con ese user name");
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/userbyid/{id}") // trae un usuari por su id
    public ResponseEntity<Object> getUserById(@PathVariable int id) { // @PathVariable trae la variable por params
        UserModel user = userService.findUser(id);
        if (user == null) {
            StatusRuta status = new StatusRuta("No se encontro el usuario con ese id");
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/deleted/{id}") // elemino un usuario por su id
    public ResponseEntity<?> userDeleted(@PathVariable int id) {
        UserModel user = userService.findUser(id);
        if (user == null) {
            StatusRuta status = new StatusRuta("No se encontro el usuario con ese id para eleminar");
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
        StatusRuta status = new StatusRuta("Usuario eleminado");
        userService.deletedUser(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
