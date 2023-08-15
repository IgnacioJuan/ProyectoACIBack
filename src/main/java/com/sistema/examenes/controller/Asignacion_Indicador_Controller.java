package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Asignacion_Indicador;
import com.sistema.examenes.entity.Modelo;
import com.sistema.examenes.services.Asignacion_Indicador_Service;
import com.sistema.examenes.services.Modelo_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sistema.examenes.repository.Asignacion_Indicador_repository;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/asignacion_indicador")
public class Asignacion_Indicador_Controller {
    @Autowired
    Asignacion_Indicador_Service Service;

    @Autowired
    Asignacion_Indicador_repository asignacion_Rep;

    @Autowired
    Modelo_Service modelo_Service;

    @GetMapping("/crear")
    public ResponseEntity<Asignacion_Indicador> crear(@RequestBody Asignacion_Indicador r) {
        try {
            r.setVisible(true);

            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Asignacion_Indicador>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Asignacion_Indicador>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Asignacion_Indicador> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return Service.delete(id);
    }

    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Asignacion_Indicador a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setVisible(false);
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Asignacion_Indicador> actualizar(@PathVariable Long id, @RequestBody Asignacion_Indicador p) {
        Asignacion_Indicador a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setModelo(p.getModelo());
                a.setIndicador(p.getIndicador());
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    // metodo post para crear un nuevo Asignacion_Indicador
    @PostMapping("/crearAsignacion")
    public ResponseEntity<Asignacion_Indicador> crearAsignacion(@RequestBody Asignacion_Indicador r) {
        try {
            r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // metodo get para obtener la lista de Asignacion_Indicador por objeto modelo
    @GetMapping("/listarAsignacion/{id}")
    public ResponseEntity<List<Asignacion_Indicador>> obtenerListaAsignacion(@PathVariable("id") Long id) {
        try {
            Modelo m = new Modelo();
            m = modelo_Service.findById(id);
            return new ResponseEntity<>(Service.findByModelo(m), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarasig_indi/{id}")
    public ResponseEntity<List<Asignacion_Indicador>> obtenerAsignacion(@PathVariable("id") Long id) {
        try {

            return new ResponseEntity<>(Service.listarAsignacion(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}