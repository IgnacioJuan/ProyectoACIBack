package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Archivo;
import com.sistema.examenes.entity.Archivo_s;
import com.sistema.examenes.entity.Evidencia;
import com.sistema.examenes.mensajes.Archivosmensajes;
import com.sistema.examenes.projection.ArchivoProjection;
import com.sistema.examenes.services.Actividad_Service;
import com.sistema.examenes.services.Archivo_Service;
import com.sistema.examenes.services.Archivoservices;
import com.sistema.examenes.services.Evidencia_Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("archivo")
@AllArgsConstructor
public class Archivo_Controller {
    @Autowired
    Archivoservices servis;
    @Autowired
    Evidencia_Service eviservis;
    @Autowired
    Archivo_Service archivoservis;
    @Autowired
    Actividad_Service actiservis;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/upload")
    public ResponseEntity<Archivosmensajes> upload(@RequestParam("file") MultipartFile[] files,
                                                   @RequestParam("descripcion") String describcion,
                                                   @RequestParam("id_evidencia") Long id_actividad) {
        String meNsaje = "";
        try {
            Actividad actividad = actiservis.findById(id_actividad);
            if (actividad == null) {
                meNsaje = "No se encontró la evidencia con id " + id_actividad;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Archivosmensajes(meNsaje));
            }
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                servis.guardar(file);
                fileNames.add(file.getOriginalFilename());
            });
            String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            String url = ServletUriComponentsBuilder.fromHttpUrl(host)
                    .path("/archivo/").path(fileNames.get(0)).toUriString();
            archivoservis.save(new Archivo_s(url.toString(), fileNames.toString().join(",",fileNames), describcion, true, actividad));
            meNsaje = "Se subieron correctamente " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new Archivosmensajes(meNsaje + "url:" + url));
        } catch (Exception e) {
            meNsaje = "Fallo al subir";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Archivosmensajes(meNsaje));
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Archivo_s>> obtenerListav() {
        try {
            return new ResponseEntity<>(archivoservis.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/archivoindi/{id_criterio}/{id_modelo}/{id_indicador}")
    public ResponseEntity<List<Archivo_s>> archivoindi(@PathVariable("id_criterio") Long id_criterio,
                                                       @PathVariable("id_modelo")Long id_modelo,
                                                       @PathVariable("id_indicador")Long id_indicador) {
        try {
            return new ResponseEntity<>(archivoservis.archivoporindicador(id_criterio,id_modelo,id_indicador), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listararchi")
    public ResponseEntity<List<ArchivoProjection>> listaarchi() {
        try {
            return new ResponseEntity<>(archivoservis.listararchi(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<List<Archivo>> getFiles() {
        List<Archivo> archivos = servis.lIstar().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(Archivo_Controller.class, "getFile"
                    , path.getFileName().toString()).build().toString();

            return new Archivo(filename, url);

        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(archivos);
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = servis.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION
                , "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/borrar/{filename:.+}")
    public ResponseEntity<Archivosmensajes> borrar(@PathVariable String filename) {
        String mensaje = "";
        try {
            mensaje = servis.borrar(filename);
            return ResponseEntity.status(HttpStatus.OK).body(new Archivosmensajes(mensaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Archivosmensajes(mensaje));
        }
    }


    @GetMapping("/buscarev/{username}")
    public ResponseEntity<List<Archivo_s>> listararchi(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(archivoservis.listararchivouser(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscararchivo/{idActi}")
    public ResponseEntity<List<Archivo_s>> listararchiActividad(@PathVariable("idActi") Long idActividad) {
        try {
            return new ResponseEntity<>(archivoservis.listararchivoActividad(idActividad), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Archivo_s as = archivoservis.findById(id);
        if (as == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                as.setVisible(false);
                return new ResponseEntity<>(archivoservis.save(as), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

}


















