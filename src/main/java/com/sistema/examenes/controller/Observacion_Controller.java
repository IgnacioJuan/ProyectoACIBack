package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Encabezado_Evaluar;
import com.sistema.examenes.entity.Observacion;
import com.sistema.examenes.services.Observacion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/observacion")
public class Observacion_Controller {
    @Autowired
    Observacion_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Observacion> crear(@RequestBody Observacion r) {
        try {
            r.setVisible(true);

            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Observacion>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/obseractividad/{id_actividad}")
    public ResponseEntity<List<Observacion>> observacionactividad(@PathVariable("id_actividad") Long id_actividad) {
        try {
            return new ResponseEntity<>(Service.observacionactividad(id_actividad), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Observacion>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Observacion> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscarobs/{username}/{id}")
    public ResponseEntity<List<Observacion>> getByUsuario(@PathVariable("username") String username,@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.observacionUsuario(username, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Observacion> eliminar(@PathVariable Long id) {
        //public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Observacion observacion) {
        Service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Observacion a = Service.findById(id);
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
    public ResponseEntity<Observacion> actualizar(@PathVariable Long id, @RequestBody Observacion p) {
        Observacion a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setObservacion(p.getObservacion());
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
    
    @GetMapping("/buscarObserByActiv/{id}")
    public ResponseEntity<List<Observacion>> getByActivi(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.listarObservacActivi(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
