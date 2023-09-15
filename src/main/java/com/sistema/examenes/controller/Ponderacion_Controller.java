package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Encabezado_Evaluar;
import com.sistema.examenes.entity.Ponderacion;
import com.sistema.examenes.projection.PonderacionProjection;
import com.sistema.examenes.services.Ponderacion_Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ponderacion")
public class Ponderacion_Controller {
    @Autowired
    Ponderacion_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Ponderacion> crear(@RequestBody Ponderacion r) {
        try {
            r.setVisible(true);

            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crearLista")
    public ResponseEntity<List<Ponderacion>> crear(@RequestBody List<Ponderacion> ponderaciones) {
        try {
            List<Ponderacion> resultados = new ArrayList<>();

            for (Ponderacion ponderacion : ponderaciones) {
                ponderacion.setVisible(true);
                resultados.add(Service.save(ponderacion));
            }

            return new ResponseEntity<>(resultados, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Ponderacion>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Ponderacion>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Ponderacion> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Ponderacion ponderacion) {
        return Service.delete(id);
    }

    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Ponderacion a = Service.findById(id);
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
    public ResponseEntity<Ponderacion> actualizar(@PathVariable Long id, @RequestBody Ponderacion p) {
        Ponderacion a = Service.findById(id);
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
    @GetMapping("/idmax/{id_modelo}")
    public ResponseEntity<List<PonderacionProjection>>idmax(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.idmax(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarPonderacionPorModelo/{id_modelo}")
    public ResponseEntity<List<PonderacionProjection>> listarPonderacionPorModelo(@PathVariable("id_modelo") Long id_modelo) {
        List<PonderacionProjection> ponderaciones = Service.listarPonderacionModelo(id_modelo);
        return new ResponseEntity<>(ponderaciones, HttpStatus.OK);
    }

    @GetMapping("/listarPonderacionPorFecha/{fecha}/{contador}")
    public ResponseEntity<List<Ponderacion>> listarPonderacionPorFecha(@PathVariable("fecha") String fecha,@PathVariable("contador") Long contador) {
        List<Ponderacion> ponderaciones = Service.listarPonderacionPorFecha(fecha,contador);
        return new ResponseEntity<>(ponderaciones, HttpStatus.OK);
    }

    @GetMapping("/listarPorFecha/{fecha}")
    public ResponseEntity<List<Ponderacion>> listarPorFecha(@PathVariable("fecha") String fecha) {
        List<Ponderacion> ponderaciones = Service.listarPorFecha(fecha);
        return new ResponseEntity<>(ponderaciones, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarponderacion/{contador}/{fecha}")
    public ResponseEntity<Void> eliminarPonderacion(@PathVariable Long contador,@PathVariable("fecha") String fecha) {
        Service.eliminarPonderacion(contador,fecha);
        return ResponseEntity.noContent().build();
    }
}