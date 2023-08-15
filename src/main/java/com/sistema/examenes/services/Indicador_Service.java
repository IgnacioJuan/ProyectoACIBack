package com.sistema.examenes.services;

import com.sistema.examenes.entity.Indicador;
import com.sistema.examenes.projection.IndicadorEvidenciasProjection;
import com.sistema.examenes.projection.IndicadorEvidenciasProjectionFull;
import com.sistema.examenes.projection.IndicadoresProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjection;

import java.util.List;

public interface Indicador_Service extends GenericService<Indicador, Long> {
    public List<Indicador> listar();
    public List<IndicadoresProjection> indicadores();
    public List<Indicador> listarPorSubcriterio(Long id_subcriterio);

    List<Indicador> obtenerIndicadores(Long id);

    List<Indicador> listarIndicadorPorCriterioModelo(Long id_criterio, Long id_modelo);

    List<Indicador> indicadoresPorCriterios(List<Long> id_criterio);
    public List<IndicadorEvidenciasProjection> obtenerDatosIndicadores(Long id_subcriterio);
    public List<IndicadorEvidenciasProjectionFull> obtenerDatosIndicadoresFull();
    List<Indicador> indicadoresPorModelo(Long id_modelo);



}
