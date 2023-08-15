package com.sistema.examenes.services;

import com.sistema.examenes.entity.Asignacion_Indicador;
import com.sistema.examenes.entity.Modelo;

import java.util.List;

public interface Asignacion_Indicador_Service extends GenericService<Asignacion_Indicador, Long> {
    public List<Asignacion_Indicador> listar();

    public List<Asignacion_Indicador> findByModelo(Modelo modelo);
    List<Asignacion_Indicador> listarAsignacion(Long id_modelo);
}
