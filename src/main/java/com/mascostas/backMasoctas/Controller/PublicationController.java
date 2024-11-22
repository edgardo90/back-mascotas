/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascostas.backMasoctas.Controller;
//

import org.springframework.web.bind.annotation.CrossOrigin;//configura los coors
import org.springframework.web.bind.annotation.RequestMethod;// sirve para configurar los coors
import org.springframework.web.bind.annotation.GetMapping; // esto lo importo yo
import org.springframework.web.bind.annotation.PathVariable; // esto para que sea por params
import org.springframework.web.bind.annotation.RequestParam; // esto sirve para venir por query
import org.springframework.web.bind.annotation.RestController; // esto lo importo yo
import org.springframework.http.HttpStatus; // le doy el status si esta ok (creo que tambien si esta todo mal)
import org.springframework.http.ResponseEntity; //para enviar datos o un mensaje en formato json
import org.springframework.web.bind.annotation.PostMapping;//ruta para el post
import org.springframework.web.bind.annotation.RequestBody; // esto sirve para traer las variables del post
import org.springframework.web.bind.annotation.ResponseBody; //
import org.springframework.web.bind.annotation.DeleteMapping; // ruta para el deleted
import org.springframework.web.bind.annotation.PutMapping; // ruta para el put
import org.springframework.beans.factory.annotation.Autowired;//// para el service , en este caso ITaskService
import java.util.List; //
import java.util.ArrayList;// esto sirve para crear un array
import java.util.*;
import java.util.stream.*;
//importo lo que cree
import com.mascostas.backMasoctas.Model.PublicationModel;
import com.mascostas.backMasoctas.Dto.PublicationDto;
import com.mascostas.backMasoctas.Model.StatusRuta; // modelo que sirve para enviar un msg en formato json
import com.mascostas.backMasoctas.Service.PublicationService;
import com.mascostas.backMasoctas.Security.Model.UserModel;
import com.mascostas.backMasoctas.Security.Service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author edgar
 */
@RestController // para emepezar hacer el ruteo
@RequestMapping("/publication") // con esto todas las rutas de abajo empieza con /publication
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}) // configuro los corrs
public class PublicationController {

    @Autowired
    private PublicationService publicationService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    //@PreAuthorize("hasRole('USER')") // con esto solo van poder ingresar esta ruta si esta logueado
    public ResponseEntity<?> createPublication(@RequestBody PublicationDto publicationModelDto) {
        Optional<UserModel> user = userService.getByUserName(publicationModelDto.getUserName());
        if (!user.isPresent()) {
            StatusRuta status = new StatusRuta("No se encontro el usuario con ese id");
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
        PublicationModel publicationModel = new PublicationModel(
                publicationModelDto.getName(),
                publicationModelDto.getType(),
                publicationModelDto.getRace(),
                publicationModelDto.getSex(),
                publicationModelDto.getSizePet(),
                publicationModelDto.getImage(),
                publicationModelDto.getDescription(),
                publicationModelDto.getDistrict(),
                publicationModelDto.getDirection(),
                publicationModelDto.getDateLost(),
                true,
                user.get()
        );
        publicationService.savePublication(publicationModel);
        Map<String, Object> response = new HashMap<>(); // es una estructura de datos que almacena pares clave-valo
        response.put("message", "publiacion creada"); //agrego una key y su valor  la key del object  va ser  "message" y "Usuario guardado" su valor
        response.put("status", "success");
        response.put("data", publicationModel);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getPublications() {
        List<PublicationModel> publications = publicationService.getPublications();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", publications);
        return ResponseEntity.ok(response); // Usamos ResponseEntity.ok() directamente para evitar problemas de tipo.
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPublication(@PathVariable Long id) {// elemino la tarea por su id ,como tasksModel esta vinculado con el modelo usuario cuando elemino la tarea tambien se elemina la tarea en usuario
        PublicationModel publicationModel = publicationService.findPublication(id); // busco la task por su id y lo guardo en una variable, .findTask(id) es una funcion que cree para buscar por su id
        //System.out.println(task); // esto es como un console.log de javaScript
        if (publicationModel == null) { // si task es null
            StatusRuta status = new StatusRuta("No se encontro la publicacion"); // creo un msg con el Status model que cree
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST); // envio un msj 
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "ok");
            response.put("data", publicationModel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/update/{id}")
    //@PreAuthorize("hasRole('USER')") // con esto solo van poder ingresar esta ruta si esta logueado
    public ResponseEntity<?> updatePublication(@PathVariable Long id, @RequestBody PublicationDto publicationDto) {
        PublicationModel existingPublication = publicationService.findPublication(id);
        if (existingPublication == null) {
            StatusRuta status = new StatusRuta("No se encontró la publicación con ese ID");
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        if (publicationDto.getName() != null) {
            existingPublication.setName(publicationDto.getName());
        }
        if (publicationDto.getType() != null) {
            existingPublication.setType(publicationDto.getType());
        }
        if (publicationDto.getRace() != null) {
            existingPublication.setRace(publicationDto.getRace());
        }
        if (publicationDto.getSex() != null) {
            existingPublication.setSex(publicationDto.getSex());
        }
        if (publicationDto.getSizePet() != null) {
            existingPublication.setSizePet(publicationDto.getSizePet());
        }
        if (publicationDto.getImage() != null) {
            existingPublication.setImage(publicationDto.getImage());
        }
        if (publicationDto.getDescription() != null) {
            existingPublication.setDescription(publicationDto.getDescription());
        }
        if (publicationDto.getDistrict() != null) {
            existingPublication.setDistrict(publicationDto.getDistrict());
        }
        if (publicationDto.getDirection() != null) {
            existingPublication.setDirection(publicationDto.getDirection());
        }
        if (publicationDto.getDateLost() != null) {
            existingPublication.setDateLost(publicationDto.getDateLost());
        }
        if (publicationDto.getIsLost() != null) {
            existingPublication.setIsLost(publicationDto.getIsLost());
        }
        publicationService.savePublication(existingPublication);
        Map<String, Object> response = new HashMap<>(); // es una estructura de datos que almacena pares clave-valo
        response.put("message", "publiacion modificada"); //agrego una key y su valor  la key del object  va ser  "message" y "Usuario guardado" su valor
        response.put("status", "success");
        response.put("data", existingPublication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('USER')") // con esto solo van poder ingresar esta ruta si esta logueado
    public ResponseEntity<Object> deletedPublication(@PathVariable Long id) {// elemino la tarea por su id ,como tasksModel esta vinculado con el modelo usuario cuando elemino la tarea tambien se elemina la tarea en usuario
        PublicationModel publicationModel = publicationService.findPublication(id); // busco la task por su id y lo guardo en una variable, .findTask(id) es una funcion que cree para buscar por su id
        //System.out.println(task); // esto es como un console.log de javaScript
        if (publicationModel == null) { // si task es null
            StatusRuta status = new StatusRuta("No se encontro la publicacion para eleminar"); // creo un msg con el Status model que cree
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST); // envio un msj que no se pudo eleminar
        } else { // caso contrario como task !== null(en javaScript) , elemino la task
            publicationService.deletePublication(id);
            StatusRuta status = new StatusRuta("Se elemino la publicacion");
            return new ResponseEntity<>(status, HttpStatus.OK);
        }
    }
}
