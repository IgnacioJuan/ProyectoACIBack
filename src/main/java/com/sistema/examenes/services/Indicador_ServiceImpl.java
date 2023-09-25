package com.sistema.examenes.services;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Indicador;
import com.sistema.examenes.projection.IndiColProjection;
import com.sistema.examenes.projection.IndicadorEvidenciasProjection;
import com.sistema.examenes.projection.IndicadorEvidenciasProjectionFull;
import com.sistema.examenes.projection.IndicadoresProjection;
import com.sistema.examenes.repository.Indicador_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Indicador_ServiceImpl extends GenericServiceImpl<Indicador, Long> implements Indicador_Service {
    @Autowired
    private Indicador_repository repository;

    @Override
    public CrudRepository<Indicador, Long> getDao() {

        return repository;
    }

    @Override
    public List<Indicador> listar() {
        return repository.listarIndicador();
    }

    @Override
    public List<IndicadoresProjection> indicadores(Long id_modelo) {
        return repository.Indicadores(id_modelo);
    }

    @Override
    public List<IndicadoresProjection> indicadoresadmin(Long id_modelo, Long id) {
        return repository.indicadoresadmin(id_modelo, id);
    }

    @Override
    public List<Indicador> listarPorSubcriterio(Long id_subcriterio) {
        return repository.listarIndicadorPorSubcriterio(id_subcriterio);
    }

    @Override
    public List<Indicador> obtenerIndicadores(Long id) {
        return repository.obtenerIndicadores(id);
    }

    @Override
    public List<Indicador> listarIndicadorPorCriterioModelo(Long id_criterio, Long id_modelo) {
        return repository.listarIndicadorPorCriterioModelo(id_criterio, id_modelo);
    }

    @Override
    public List<Indicador> indicadoresPorCriterios(List<Long> id_criterio) {
        return repository.indicadoresPorCriterios(id_criterio);
    }
    @Override
    public List<Indicador> indicadoresPorCriteriosPruebaCuali(List<Long> id_criterio) {
        return repository.indicadoresPorCriteriosPruebaCuali(id_criterio);
    }
    @Override
    public List<Indicador> indicadoresPorCriteriosPruebaCuanti(List<Long> id_criterio) {
        return repository.indicadoresPorCriteriosPruebaCuanti(id_criterio);
    }

    @Override
    public List<Indicador> indicadoresPorCriteriosCuali() {return repository.indicadoresPorCriteriosCuali();}
    @Override
    public List<Indicador> indicadoresPorCriteriosCuanti() {return repository.indicadoresPorCriteriosCuanti();}

    @Override
    public List<IndicadorEvidenciasProjection> obtenerDatosIndicadores(Long id_subcriterio){
        return repository.obtenerIndicadoresConCantidadEvidencia(id_subcriterio);
    };
    @Override
    public List<IndicadorEvidenciasProjectionFull> obtenerDatosIndicadoresFull(){
        return repository.obtenerIndicadoresConCantidadEvidenciaFull();
    };
    @Override
    public List<Indicador> indicadoresPorModelo(Long id_modelo) {
        return repository.indicadoresPorModelo(id_modelo);
    }

    @Override
    public List<IndiColProjection> indicadorval(Long id_modelo) {
        return repository.indicadorval(id_modelo);
    }

    @Override
    public List<Indicador> indicadorPorSubcriterio(Long id_subcriterio, Long id_modelo) {
        return repository.indicadorPorSubcriterio(id_subcriterio, id_modelo);
    }

    @Override
    public List<IndicadoresProjection> indicadoresresp(Long id_modelo, Long id) {
        return repository.indicadoresresp(id_modelo, id);
    }

    @Override
    public List<IndiColProjection> indicadorvaladmin(Long id_modelo, Long id) {
        return repository.indicadorvaladmin(id_modelo, id);
    }
}
