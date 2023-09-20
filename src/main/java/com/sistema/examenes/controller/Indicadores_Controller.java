package com.sistema.examenes.controller;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Indicador;
import com.sistema.examenes.projection.*;
import com.sistema.examenes.services.Indicador_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/indicadores")
public class Indicadores_Controller {
    @Autowired
    Indicador_Service Service;

    @PostMapping("/crear")
    public ResponseEntity<Indicador> crear(@RequestBody Indicador r) {
        try {
            r.setVisible(true);
            return new ResponseEntity<>(Service.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Indicador>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.listar(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarindi/{id_modelo}")
    public ResponseEntity<List<IndicadoresProjection>> listaIndi(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.indicadores(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarindiad/{id_modelo}/{id}")
    public ResponseEntity<List<IndicadoresProjection>> listaIndiad(@PathVariable("id_modelo") Long id_modelo,@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.indicadoresadmin(id_modelo,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarindicadresponsable/{id_modelo}/{id}")
    public ResponseEntity<List<IndicadoresProjection>> listaIndirespo(@PathVariable("id_modelo") Long id_modelo,@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.indicadoresresp(id_modelo,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscarindicador/{id}")
    public ResponseEntity<List<Indicador>> obtenerCriterios(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.obtenerIndicadores(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subcritindicador/{id_subcriterio}/{id_modelo}")
    public ResponseEntity<List<Indicador>> indicadoressub(@PathVariable("id_subcriterio") Long id_subcriterio,@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service. indicadorPorSubcriterio(id_subcriterio,id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Indicador> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(Service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Indicador indicador = Service.findById(id);
        if (indicador == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                indicador.setVisible(false);
                return new ResponseEntity<>(Service.save(indicador), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Indicador> actualizar(@PathVariable Long id, @RequestBody Indicador p) {
        Indicador indicador = Service.findById(id);
        if (indicador == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                indicador.setNombre(p.getNombre());
                indicador.setDescripcion(p.getDescripcion());
                indicador.setPeso(p.getPeso());
                indicador.setTipo(p.getTipo());
                indicador.setEstandar(p.getEstandar());
                return new ResponseEntity<>(Service.save(indicador), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @PutMapping("/ponderacion/{id}")
    public ResponseEntity<Indicador> actualizarPonderacion(@PathVariable Long id, @RequestBody Indicador p) {
        Indicador indicador = Service.findById(id);
        if (indicador == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                indicador.setValor_obtenido(p.getValor_obtenido());
                indicador.setPorc_obtenido(p.getPorc_obtenido());
                indicador.setPorc_utilida_obtenida(p.getPorc_utilida_obtenida());
                return new ResponseEntity<>(Service.save(indicador), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    // consumir metodo listarPorSubcriterio
    @GetMapping("/listarPorSubcriterio/{id_subcriterio}")
    public ResponseEntity<List<Indicador>> listarPorSubcriterio(@PathVariable("id_subcriterio") Long id_subcriterio) {
        try {
            return new ResponseEntity<>(Service.listarPorSubcriterio(id_subcriterio), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // consumir metodo listarIndicadorPorCriterioModelo
    @GetMapping("/listarIndicadorPorCriterioModelo/{id_criterio}/{id_modelo}")
    public ResponseEntity<List<Indicador>> listarIndicadorPorCriterioModelo(
            @PathVariable("id_criterio") Long id_criterio,
            @PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.listarIndicadorPorCriterioModelo(id_criterio, id_modelo),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/indicadoresPorCriterios")
    public ResponseEntity<List<Indicador>> indicadoresPorCriterios(
            @RequestParam("idCriterios") List<Long> idCriterios) {
        try {
            return new ResponseEntity<>(Service.indicadoresPorCriterios(idCriterios), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indicadoresPorCriteriosPruebaCL")
    public ResponseEntity<List<Indicador>> indicadoresPorCriteriosPruebaCuali(
            @RequestParam("idCriterios") List<Long> idCriterios) {
        try {
            return new ResponseEntity<>(Service.indicadoresPorCriteriosPruebaCuali(idCriterios), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indicadoresPorCriteriosPruebaCT")
    public ResponseEntity<List<Indicador>> indicadoresPorCriteriosPruebaCuanti(
            @RequestParam("idCriterios") List<Long> idCriterios) {
        try {
            return new ResponseEntity<>(Service.indicadoresPorCriteriosPruebaCuanti(idCriterios), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indicadoresPorCriteriosCuali")
    public ResponseEntity<List<Indicador>> indicadoresPorCriteriosCuali() {
        try {
            List<Indicador> indicadores = Service.indicadoresPorCriteriosCuali();
            return new ResponseEntity<>(indicadores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indicadoresPorCriteriosCuanti")
    public ResponseEntity<List<Indicador>> indicadoresPorCriteriosCuanti() {
        try {
            List<Indicador> indicadores = Service.indicadoresPorCriteriosCuanti();
            return new ResponseEntity<>(indicadores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/datosIndicadores/{id_subcriterio}")
    public List<IndicadorEvidenciasProjection> obtenerDatosSubcriterios(@PathVariable("id_subcriterio") Long id_subcriterio) {
        return Service.obtenerDatosIndicadores(id_subcriterio);
    }

    @GetMapping("/datosIndicadoresFull")
    public List<IndicadorEvidenciasProjectionFull> obtenerDatosSubcriteriosFull() {
        return Service.obtenerDatosIndicadoresFull();
    }

    @GetMapping("/indicadorespormodelo/{id_modelo}")
    public ResponseEntity<List<Indicador>> indicadoresPorModelo(
            @PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.indicadoresPorModelo(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indicadorval/{id_modelo}")
    public ResponseEntity<List<IndiColProjection>> indicadorval(@PathVariable("id_modelo") Long id_modelo) {
        try {
            return new ResponseEntity<>(Service.indicadorval(id_modelo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping("/listar")
    public ResponseEntity<List<EvidenciaProjection>> obtenerLista() {
        try {
            return new ResponseEntity<>(Service.listarprueba(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}