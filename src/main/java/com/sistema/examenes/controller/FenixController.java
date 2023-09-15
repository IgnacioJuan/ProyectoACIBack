package com.sistema.examenes.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.examenes.fenix.repository.DocentesfenixRepository;
import com.sistema.examenes.fenix.entity.Docentesfenix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Controller para fenix
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/fenix")
public class FenixController {

    // metodos para el controlador de fenix
    @Autowired
    DocentesfenixRepository docentefenixRepository;

    // }

    // metodo para listar todos los docentes
    @GetMapping(path = { "/listar" })
    public List<Docentesfenix> findAll() {
        return docentefenixRepository.findAll();
    }

    // metodo para buscar docente por cedula
    @GetMapping(path = { "/cedula/{cedula}" })
    public List<Docentesfenix> findByCedula(@PathVariable("cedula") String cedula) {
        return docentefenixRepository.findByCedula(cedula);
    }

    // metodo para buscar docente por primer apellido
    @GetMapping(path = { "/p-apellido/{primer_apellido}" })
    public List<Docentesfenix> findByPrimer_Apellido(@PathVariable("primer_apellido") String primer_apellido) {
        return docentefenixRepository.findByPrimer_Apellido(primer_apellido);
    }
    @GetMapping(path = { "/p-nombre/{primer_nombre}" })
    public List<Docentesfenix> findByPrimer_nombre(@PathVariable("primer_nombre") String primer_nombre) {
        return docentefenixRepository.findByPrimer_Nombre(primer_nombre);
    }

    @GetMapping("/p-nombres/{primer_nombre}/{primer_apellido}")
    public List<Docentesfenix> findBynombresCompletos(@PathVariable("primer_nombre") String primer_nombre, @PathVariable("primer_apellido") String primer_apellido) {
        return docentefenixRepository.findBynombresCompletos(primer_nombre, primer_apellido);
    }
    // metodo para buscar docente por segundo apellido
    @GetMapping(path = { "/s-apellido/{segundo_apellido}" })
    public List<Docentesfenix> findBySegundoApellido(@PathVariable("segundo_apellido") String segundo_apellido) {
        return docentefenixRepository.findBySegundo_Apellido(segundo_apellido);
    }

    // metodo para buscar docente por primer y segundo apellido
    @GetMapping(path = { "/apellidos/{primer_apellido}/{segundo_apellido}" })
    public List<Docentesfenix> findByPrimer_ApellidoAndSegundo_Apellido(
            @PathVariable("primer_apellido") String primer_apellido,
            @PathVariable("segundo_apellido") String segundo_apellido) {
        return docentefenixRepository.findByPrimer_ApellidoAndSegundo_Apellido(primer_apellido, segundo_apellido);
    }
}
