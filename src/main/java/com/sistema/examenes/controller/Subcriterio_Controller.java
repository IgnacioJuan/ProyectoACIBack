package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Subcriterio;
import com.sistema.examenes.projection.CriterioSubcriteriosProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjectionFull;
import com.sistema.examenes.services.Subcriterio_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/subcriterio")
public class Subcriterio_Controller {
    @Autowired
    Subcriterio_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Subcriterio> crear(@RequestBody Subcriterio r) {
        try {
            r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Subcriterio>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Subcriterio> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Subcriterio subcriterio = Service.findById(id);
        if (subcriterio == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                subcriterio.setVisible(false);
                return new ResponseEntity<>(Service.save(subcriterio), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Subcriterio> actualizar(@PathVariable Long id, @RequestBody Subcriterio p) {
        Subcriterio subcriterio = Service.findById(id);
        if (subcriterio == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                subcriterio.setNombre(p.getNombre());
                subcriterio.setDescripcion(p.getDescripcion());
                return new ResponseEntity<>(Service.save(subcriterio), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/listarPorCriterio/{id_criterio}")
    public ResponseEntity<List<Subcriterio>> listarPorCriterio(@PathVariable("id_criterio") Long id_criterio) {
        try {
            return new ResponseEntity<>(Service.listarPorCriterio(id_criterio), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/datosSubcriterios/{id_criterio}")
    public List<SubcriterioIndicadoresProjection> obtenerDatosSubcriterios(@PathVariable("id_criterio") Long id_criterio) {
        return Service.obtenerDatosSubcriterios(id_criterio);
    }

    @GetMapping("/subcritindi/{id_criterio}/{id_modelo}")
    public List<SubcriterioIndicadoresProjection> obtenerSubcriterios(@PathVariable("id_criterio") Long id_criterio,@PathVariable("id_modelo") Long id_modelo) {
        return Service.obtenerSubcriterios(id_criterio, id_modelo);
    }
    @GetMapping("/datosSubcriteriosFull")
    public List<SubcriterioIndicadoresProjectionFull> obtenerDatosSubcriteriosFull() {
        return Service.obtenerDatosSubcriteriosFull();
    }
}