package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Detalle_Evaluacion;
import com.sistema.examenes.services.Detalle_Evaluacion_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/detalle_evaluacion")
public class Detalle_Evaluacion_Controller {
    @Autowired
    Detalle_Evaluacion_Service Service;


    @PostMapping("/crear")
    public ResponseEntity<Detalle_Evaluacion> crear(@RequestBody Detalle_Evaluacion r) {
        Boolean existe = Service.existeeva(r.getEvidencia().getId_evidencia(), r.getUsuario().getId(), r.getId_modelo());
        System.out.println("existe detalle "+existe);
        try {
            if (existe) {
                Long iddet=Service.iddetalle(r.getEvidencia().getId_evidencia(), r.getUsuario().getId(), r.getId_modelo());
                Detalle_Evaluacion detalleExistente = Service.findById(iddet);

                if (detalleExistente != null) {
                    if(!r.isEstado()){
                        r.setVisible(true);
                        return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
                    }else{
                    detalleExistente.setFecha(r.getFecha());
                    detalleExistente.setObservacion(r.getObservacion());
                    detalleExistente.setEstado(r.isEstado());
                    return new ResponseEntity<>(Service.save(detalleExistente), HttpStatus.OK);}
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                r.setVisible(true);
                return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Detalle_Evaluacion>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarv")
    public ResponseEntity<List<Detalle_Evaluacion>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Detalle_Evaluacion> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarporEviRecha/{idEvi}")
    public ResponseEntity<List<Detalle_Evaluacion>> listarPorEvidencia(@PathVariable("idEvi") Long idEvidencia) {
        try {
            return new ResponseEntity<>(Service.listarDetalleEvaluacion(idEvidencia), HttpStatus.OK);
        } catch (Exception e) {;
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Detalle_Evaluacion detalle_evaluacion) {
        return Service.delete(id);
    }
    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Detalle_Evaluacion a = Service.findById(id);
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
    public ResponseEntity<Detalle_Evaluacion> actualizar(@PathVariable Long id, @RequestBody Detalle_Evaluacion p) {
        Detalle_Evaluacion a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
}
