package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Encabezado_Evaluar;
import com.sistema.examenes.entity.Evidencia;
import com.sistema.examenes.projection.AsigEvidProjection;
import com.sistema.examenes.projection.EvidenciaCalProjection;
import com.sistema.examenes.projection.EvidenciaProjection;
import com.sistema.examenes.projection.EvidenciasProjection;
import com.sistema.examenes.services.Evidencia_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/evidencia")
public class Evidencia_Controller {
    @Autowired
    Evidencia_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Evidencia> crear(@RequestBody Evidencia r) {
        try {
            r.setVisible(true);

            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Evidencia>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarv")
    public ResponseEntity<List<Evidencia>> obtenerListav() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarvAsigna/{id}")
    public ResponseEntity<List<Evidencia>> obtenerListavAsigna(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.listarEvidenciaAsigna(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/evidenciaprobada/{id_modelo}")
    public ResponseEntity<List<EvidenciasProjection>> evidenciaprobada(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.evidenciaAprobada(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/evidenciarechazada/{id_modelo}")
    public ResponseEntity<List<EvidenciasProjection>> evidenciarechazada(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.evidenciaRechazada(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/evicriterio/{idcriterio}")
    public ResponseEntity<List<Evidencia>> evidenciaindicador(@PathVariable("idcriterio") Long idcriterio) {
        try {
            return new ResponseEntity<>(Service.evidenciacriterio(idcriterio), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/eviasigtab/{idcriterio}")
    public ResponseEntity<List<AsigEvidProjection>> evidenciatabla(@PathVariable("idcriterio") Long idcriterio) {
        try {
            return new ResponseEntity<>(Service.evidenciatab(idcriterio), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/eviasigadmin/{idUser}")
    public ResponseEntity<List<AsigEvidProjection>> evidenciaadmintabla(@PathVariable("idUser") Long idUser) {
        try {
            return new ResponseEntity<>(Service.listarEvidenciaAdmin(idUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Evidencia> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarev/{username}")
    public ResponseEntity<List<Evidencia>> buscarEvidencia(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(Service.evidenciaUsuario(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/evidenuser/{username}")
    public ResponseEntity<List<EvidenciaProjection>> evidenciauser(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(Service.evidenUsuario(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/evidenciacal/{id_evidencia}/{id_modelo}")
    public ResponseEntity<EvidenciaCalProjection> evidenciacal(@PathVariable("id_evidencia") Long id_evidencia,@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.evidenciacal(id_evidencia,id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Evidencia evidencia) {
        return Service.delete(id);
    }

    @PutMapping("/eliminarlogic/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Evidencia a = Service.findById(id);
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
    public ResponseEntity<Evidencia> actualizar(@PathVariable Long id, @RequestBody Evidencia p) {
        Evidencia a = Service.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setDescripcion(p.getDescripcion());
                a.setEstado(p.getEstado());
                return new ResponseEntity<>(Service.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/listarEvidenciaPorIndicador/{id_indicador}")
    public ResponseEntity<List<Evidencia>> listarEvidenciaPorIndicador(
            @PathVariable("id_indicador") Long id_indicador) {
        try {
            return new ResponseEntity<>(Service.listarEvidenciaPorIndicador(id_indicador), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}