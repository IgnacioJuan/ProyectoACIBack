package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Indicador;
import com.sistema.examenes.entity.Subcriterio;
import com.sistema.examenes.projection.CriteProjection;
import com.sistema.examenes.projection.CriterioSubcriteriosProjection;
import com.sistema.examenes.projection.IdCriterioProjection;
import com.sistema.examenes.projection.ValoresProjection;
import com.sistema.examenes.services.Criterio_Service;
import com.sistema.examenes.services.Indicador_Service;
import com.sistema.examenes.services.Subcriterio_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/criterio")
public class Criterio_Controller {
    @Autowired
    Criterio_Service Service;
    @Autowired
    Subcriterio_Service subcriterioService;
    @Autowired
    Indicador_Service indicadorService;

    @GetMapping("/datos")
    public ResponseEntity<String> obtenerDatos() {
        List<Criterio> criterios = Service.listar();

        for (Criterio criterio : criterios) {
            List<Subcriterio> subcriterios = subcriterioService.listarPorCriterio(criterio.getId_criterio());
            for (Subcriterio subcriterio : subcriterios) {
                List<Indicador> indicadores = indicadorService.listarPorSubcriterio(subcriterio.getId_subcriterio());
                Set<Indicador> indicadoresSet = new HashSet<>(indicadores);
                subcriterio.setLista_indicadores(indicadoresSet);
            }
            Set<Subcriterio> indicadoresSet = new HashSet<>(subcriterios);
            criterio.setLista_subcriterios((indicadoresSet));
        }

        return new ResponseEntity<>(criterios.toString(), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<Criterio> crear(@RequestBody Criterio r) {
        try {
            r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Criterio>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarcriterios")
    public ResponseEntity<List<Criterio>> obtenerCriterio() {
        try {
            return new ResponseEntity<>(Service.obtenerCriterios(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/listarcriteriosMode")
    public ResponseEntity<List<Criterio>> obtenerCriterioModelo() {
        try {
            return new ResponseEntity<>(Service.obtenerCriterioModelo(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/listarcriteriosMId/{id}")
    public ResponseEntity<List<Criterio>> obtenerCriterioModeloId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.obtenerCriterioIdModelo(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/datosuser/{id}/{id_modelo}")
    public ResponseEntity<List<CriteProjection>> criterioadmi(@PathVariable("id") Long id, @PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.actividadesusuario(id,id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarvalores/{id_modelo}")
    public ResponseEntity<List<ValoresProjection>> listarvalores(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.listarvalores(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/valorescriterio/{id_modelo}/{nombre}")
    public ResponseEntity<List<ValoresProjection>> valorescriterio(@PathVariable("id_modelo") Long id_modelo,@PathVariable("nombre") String nombre) {
        try {
            return new ResponseEntity<>(Service.valoresporcriterio(id_modelo,nombre), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listvalad/{id_modelo}/{id}")
    public ResponseEntity<List<ValoresProjection>> listarvaladmin(@PathVariable("id_modelo") Long id_modelo,@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.listarvaladmin(id_modelo,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Criterio> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/idcriterio/{nombre}")
    public ResponseEntity<IdCriterioProjection> getidCrite(@PathVariable("nombre") String nombre) {
        try {
            return new ResponseEntity<>(Service.idcriterio(nombre), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Criterio criterio = Service.findById(id);
        if (criterio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                criterio.setVisible(false);
                return new ResponseEntity<>(Service.save(criterio), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Criterio> actualizar(@PathVariable Long id, @RequestBody Criterio p) {
        Criterio criterio = Service.findById(id);
        if (criterio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                criterio.setId_criterio(id);
                criterio.setNombre(p.getNombre());
                criterio.setDescripcion(p.getDescripcion());
                return new ResponseEntity<>(Service.save(criterio), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/obtenerCriteriosUltimoModelo")
    public ResponseEntity<List<Criterio>> obtenerCriteriosUltimoModelo() {
        try {
            return new ResponseEntity<>(Service.obtenerCriteriosUltimoModelo(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // List<Criterio> listarCriterioPorIndicador(Long id_indicador);
    @GetMapping("/listarCriterioPorIndicador/{id}")
    public ResponseEntity<List<Criterio>> listarCriterioPorIndicador(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.listarCriterioPorIndicador(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/datosCriterios")
    public List<CriterioSubcriteriosProjection> obtenerDatosCriterios() {
        return Service.obtenerDatosCriterios();
    }

}