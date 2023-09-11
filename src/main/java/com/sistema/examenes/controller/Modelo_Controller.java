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

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/modelo")
public class  Modelo_Controller {
    @Autowired
    Modelo_Service Service;
    /*@PostConstruct
    public void init() throws ParseException {
        Modelo r=new Modelo();
        String inicio = "05/10/2025";
        String fina = "10/09/2025";
        String fin = "10/06/2026";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date ini=dateFormat.parse(inicio);
        Date fini=dateFormat.parse(fin);
        Date finia=dateFormat.parse(fina);
        r.setFecha_fin(ini);
        r.setFecha_inicio(fini);
        r.setFecha_final_act(finia);
        r.setVisible(true);
        r.setNombre("Modelo 2");
        r.setUsuario(null);
        crear(r);
    }*/
    @PostMapping("/crear")
    public ResponseEntity<Modelo> crear(@RequestBody Modelo r) {

        try {
            //Convierto a fecha sql para poder consultar
            java.sql.Date fechaSqli = new java.sql.Date(r.getFecha_inicio().getTime());
            java.sql.Date fechaSqlf = new java.sql.Date(r.getFecha_fin().getTime());
            boolean existe =Service.existefecha(fechaSqli.toString(),fechaSqlf.toString());
            if (existe) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }else{
                    r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);}
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
                java.sql.Date fechaSqli = new java.sql.Date(p.getFecha_inicio().getTime());
                java.sql.Date fechaSqlf = new java.sql.Date(p.getFecha_fin().getTime());
                boolean existe =Service.fechaeditar(fechaSqli.toString(),fechaSqlf.toString());

                if (existe) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }else{
                a.setFecha_final_act(p.getFecha_final_act());
                a.setNombre(p.getNombre());
                a.setFecha_fin(p.getFecha_fin());
                a.setFecha_inicio(p.getFecha_inicio());
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);}
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
    @GetMapping("/listcritedes/{id_modelo}/{nombre}")
    public ResponseEntity<List<criteriosdesprojection>> listcritedescarga(@PathVariable("id_modelo") Long id_modelo,
                                                                          @PathVariable("nombre") String nombre) {
        try {
            return new ResponseEntity<>(Service.listicritedes(id_modelo,nombre), HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listcritmod/{id_criterio}/{id_modelo}")
    public ResponseEntity<List<criteriosdesprojection>> listcritemod(@PathVariable("id_criterio") Long id_criterio,@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.listcritmodel(id_criterio,id_modelo), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listcriterioadmin/{id_modelo}/{id}")
    public ResponseEntity<List<criteriosdesprojection>> criterioadminis(@PathVariable("id_modelo") Long id_modelo,
                                                                          @PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.criterioadmin(id_modelo,id), HttpStatus.OK);
        } catch (Exception e) {
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
