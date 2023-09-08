package com.sistema.examenes.services;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.projection.CriterioSubcriteriosProjection;
import com.sistema.examenes.projection.IdCriterioProjection;
import com.sistema.examenes.projection.ValoresProjection;

import java.util.List;

public interface Criterio_Service extends GenericService<Criterio, Long> {

    public List<Criterio> listar();

    public List<Criterio> obtenerCriterios();

    public List<Criterio> obtenerCriterioModelo();

    public List<Criterio> obtenerCriterioIdModelo(Long id);

    public List<Criterio> obtenerCriteriosUltimoModelo();

    // listarCriterioPorIndicador de repositorio
    public List<Criterio> listarCriterioPorIndicador(Long id_indicador);

    //Lista de criterios para el flujo
    public List<CriterioSubcriteriosProjection> obtenerDatosCriterios();
    public List<ValoresProjection>  listarvalores(Long id_modelo);
    List<ValoresProjection> listarvaladmin(Long id_modelo,Long id);
    IdCriterioProjection idcriterio(String nombre);

}
