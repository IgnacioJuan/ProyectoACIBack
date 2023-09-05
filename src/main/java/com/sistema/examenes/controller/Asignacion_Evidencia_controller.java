package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Asignacion_Evidencia;
import com.sistema.examenes.services.Asignacion_Evidencia_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/asignacionevidencia")
public class Asignacion_Evidencia_controller {
    @Autowired
    Asignacion_Evidencia_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Asignacion_Evidencia> crear(@RequestBody Asignacion_Evidencia r) {
        try {
            r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Asignacion_Evidencia>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarv")
    public ResponseEntity<List<Asignacion_Evidencia>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Asignacion_Evidencia> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Actividad actividad) {
        return Service.delete(id);
    }
    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarloginc(@PathVariable Long id) {
        Asignacion_Evidencia asignacion_evidencia = Service.findById(id);
        if (asignacion_evidencia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                asignacion_evidencia.setVisible(false);
                return new ResponseEntity<>(Service.save(asignacion_evidencia), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/elimasig/{id}/{id_evi}/{id_usuario}/{id_modelo}")
    public ResponseEntity<?> eliminarasig(@PathVariable Long id, @PathVariable Long id_evi, @PathVariable Long id_usuario, @PathVariable Long id_modelo) {
        Asignacion_Evidencia asignacion_evidencia = Service.findById(id);
        System.out.println("Prueba asignacion usuario"+id_usuario+" evidencia "+id_evi+" modelo "+id_modelo);
        if (asignacion_evidencia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                Boolean existe=Service.verificarAsignacionUsuario(id_usuario,id_evi,id_modelo);
                if(existe){
                asignacion_evidencia.setVisible(false);
                return new ResponseEntity<>(Service.save(asignacion_evidencia), HttpStatus.CREATED);
                }else{
                    String mensaje="Solo el administrador del criterio puede eliminar la asignación";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @GetMapping("/listarEviUsua/{username}")
    public ResponseEntity<List<Asignacion_Evidencia>> listarAsigEvi(@PathVariable("username") String  username) {
        try {
            return new ResponseEntity<>(Service.listarporUsuario(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Asignacion_Evidencia> actualizar(@PathVariable Long id,@RequestBody Asignacion_Evidencia p) {
        Asignacion_Evidencia asignacion_evidencia = Service.findById(id);
        if (asignacion_evidencia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                asignacion_evidencia.setEvidencia(p.getEvidencia());
                return new ResponseEntity<>(Service.save(asignacion_evidencia), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
}
