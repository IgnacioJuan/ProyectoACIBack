package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.projection.ActivAprobadaProjection;
import com.sistema.examenes.projection.ActividadesProjection;
import com.sistema.examenes.services.Actividad_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/actividad")
public class Actividad_Controller {
    @Autowired
    Actividad_Service Service;



    @PostMapping("/crear")
    public ResponseEntity<Actividad> crear(@RequestBody Actividad r) {
        try {
            r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Actividad>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarv")
    public ResponseEntity<List<Actividad>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/listaratrasa")
    public ResponseEntity<List<Actividad>> obtenerListaAtra() {

        try {
            return new ResponseEntity<>(Service.listaAtrasada(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/actividadatrasa/{id_modelo}")
    public ResponseEntity<List<ActivAprobadaProjection>> actividadAtra(@PathVariable("id_modelo") Long id_modelo) {

        try {
            return new ResponseEntity<>(Service.actividadAtrasada(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/actividadaprobada/{id_modelo}")
    public ResponseEntity<List<ActivAprobadaProjection>> actividadApro(@PathVariable("id_modelo") Long id_modelo) {

        try {
            return new ResponseEntity<>(Service.actividadApr(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/actividadpendiente/{id_modelo}")
    public ResponseEntity<List<ActivAprobadaProjection>> actividadpendiente(@PathVariable("id_modelo") Long id_modelo) {

        try {
            return new ResponseEntity<>(Service.actividadpendiente(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listarCumpli")
    public ResponseEntity<List<Actividad>> obtenerListaCump() {
        try {
            return new ResponseEntity<>(Service.listaCumplida(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/listarActAtrasa")
    public ResponseEntity<List<Actividad>> obtenerListaEviAtras() {
        try {
            System.out.println(Service.listaEvidAtrasada());
            return new ResponseEntity<>(Service.listaEvidAtrasada(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listaractiv/{id_modelo}")
    public ResponseEntity<List<ActividadesProjection>> obtactiv(@PathVariable("id_modelo")Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.actividadCont(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage().toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscaractiv/{id}")
    public ResponseEntity<List<Actividad>> getByACId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.actividadUsu(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/buscar/")
//    public ResponseEntity<List<?>> buscar(@RequestParam("nombre") String nombre) {
//        try {
//            if (nombre.trim().isEmpty()) {
//                List<Actividad> actividads = this.Service.findByAll();
//                return new ResponseEntity<>(actividads, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(Service.findByNombreContainingIgnoreCase(nombre), HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /* @GetMapping("/buscar/{id}")
     public ResponseEntity<Actividad> getById(@PathVariable("id") Long id) {
         try {
             return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }*/
    @GetMapping("/buscarusuario/{usenname}")
    public ResponseEntity <List<Actividad>> listarporUsuario(@PathVariable("usenname") String username) {

        try {
            return new ResponseEntity<>(Service.listarporusuario(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return Service.delete(id);

    }
    @GetMapping("/buscarporEvide/{idEviden}")
    public ResponseEntity <List<Actividad>> listarporEvidencia(@PathVariable("idEviden") Long idEvidencia) {
        try {
            return new ResponseEntity<>(Service.listarporEvidencia(idEvidencia), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Actividad a = Service.findById(id);
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

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Actividad t, @PathVariable(value = "id")  Long id) {
        Actividad current = Service.findById(id);
        current.setNombre(t.getNombre());
        current.setDescripcion(t.getDescripcion());
        current.setFecha_inicio(t.getFecha_inicio());
        current.setFecha_fin(t.getFecha_fin());
        current.setEstado(t.getEstado());
        return new ResponseEntity<>(Service.save(current), HttpStatus.OK);
    }
    
    @GetMapping("/buscarByUsuario/{id}")
    public ResponseEntity <List<Actividad>> listarByUsuario(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.listaActByUsuario(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PutMapping("/actualizar/{id}")
    public ResponseEntity<Actividad> actualizar(@PathVariable Long id, @RequestBody Actividad p) {
        Actividad a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setNombre(p.getNombre());
                a.setDescripcion(p.getDescripcion());
                a.setFecha_inicio(p.getFecha_inicio());
                a.setFecha_fin(p.getFecha_fin());

                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }*/
}