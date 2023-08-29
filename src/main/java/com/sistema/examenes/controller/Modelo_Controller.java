package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Modelo;
import com.sistema.examenes.projection.ModelIndiProjection;
import com.sistema.examenes.projection.ModeloVistaProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjectionFull;
import com.sistema.examenes.projection.criteriosdesprojection;
import com.sistema.examenes.services.Modelo_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/modelo")
public class  Modelo_Controller {
    @Autowired
    Modelo_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Modelo> crear(@RequestBody Modelo r) {
        try {
            r.setVisible(true);

            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Modelo>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Modelo>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Modelo> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarMax")
    public ResponseEntity<Modelo> getByIdMaximo() {
        try {
            return new ResponseEntity<>(Service.listarMaximo(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Modelo modelo) {
        return Service.delete(id);
    }

    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Modelo a = Service.findById(id);
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
    public ResponseEntity<Modelo> actualizar(@PathVariable Long id, @RequestBody Modelo p) {
        Modelo a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setFecha_final_act(p.getFecha_final_act());
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Modelo> modificar(@PathVariable Long id, @RequestBody Modelo p) {
        Modelo a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setFecha_final_act(p.getFecha_final_act());
                a.setNombre(p.getNombre());
                a.setFecha_fin(p.getFecha_fin());
                a.setFecha_inicio(p.getFecha_inicio());
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @GetMapping("/listarModeloExcepto/{id}")
    public ResponseEntity<List<Modelo>> listarModeloExcepto(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.listarModeloExcepto(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listmodelindi/{id_modelo}")
    public ResponseEntity<List<ModelIndiProjection>> listindiModelo(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.listindiModelo(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listcritedes/{id_modelo}")
    public ResponseEntity<List<criteriosdesprojection>> listcritedescarga(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.listicritedes(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("erroor john"+e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listcritedesNOM/{id_modelo}/{nombre}")
    public ResponseEntity<List<criteriosdesprojection>> listarCriterios(
            @PathVariable("id_modelo") Long id_modelo,
            @PathVariable("nombre") String nombre) {
        try {
            List<criteriosdesprojection> criterios =Service.listicrinom(id_modelo, nombre);
            return new ResponseEntity<>(criterios, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/datosModelo")
    public List<ModeloVistaProjection> obtenerDatosModelo() {
        return Service.obtenerDatosModelo();
    }
}
