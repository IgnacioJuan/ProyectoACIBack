package com.sistema.examenes.services;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Indicador;
import com.sistema.examenes.projection.*;

import java.util.List;

public interface Indicador_Service extends GenericService<Indicador, Long> {
    public List<Indicador> listar();
    public List<IndicadoresProjection> indicadores(Long id_modelo);
    List<IndicadoresProjection> indicadoresadmin(Long id_modelo,Long id);
    public List<Indicador> listarPorSubcriterio(Long id_subcriterio);

    List<Indicador> obtenerIndicadores(Long id);

    List<Indicador> listarIndicadorPorCriterioModelo(Long id_criterio, Long id_modelo);

    List<Indicador> indicadoresPorCriterios(List<Long> id_criterio);
    List<Indicador> indicadoresPorCriteriosPruebaCuali(List<Long> id_criterio);
    List<Indicador> indicadoresPorCriteriosPruebaCuanti(List<Long> id_criterio);
    List<Indicador> indicadoresPorCriteriosCuali();
    List<Indicador> indicadoresPorCriteriosCuanti();
    public List<IndicadorEvidenciasProjection> obtenerDatosIndicadores(Long id_subcriterio);
    public List<IndicadorEvidenciasProjectionFull> obtenerDatosIndicadoresFull();
    List<Indicador> indicadoresPorModelo(Long id_modelo);
    List<IndiColProjection> indicadorval(Long id_modelo);

    List<Indicador> indicadorPorSubcriterio(Long id_subcriterio,Long id_modelo);
    List<IndicadoresProjection> indicadoresresp(Long id_modelo,Long id);
}
