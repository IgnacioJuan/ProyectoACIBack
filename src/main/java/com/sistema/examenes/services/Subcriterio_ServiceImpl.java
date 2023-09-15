package com.sistema.examenes.services;

import com.sistema.examenes.entity.Subcriterio;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjectionFull;
import com.sistema.examenes.repository.Subcriterio_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Subcriterio_ServiceImpl extends GenericServiceImpl<Subcriterio, Long> implements Subcriterio_Service {
    @Autowired
    private Subcriterio_repository repository;

    @Override
    public CrudRepository<Subcriterio, Long> getDao() {

        return repository;
    }

    @Override
    public List<Subcriterio> listar() {
        return repository.listarSubcriterio();
    }

    @Override
    public List<Subcriterio> listarPorCriterio(Long id_criterio) {
        return repository.listarSubcriterioPorCriterio(id_criterio);
    }
    public List<SubcriterioIndicadoresProjection> obtenerDatosSubcriterios(Long id_criterio){
        return repository.obtenerSubcirteriosConCantidadIndicador(id_criterio);
    };

    public List<SubcriterioIndicadoresProjectionFull> obtenerDatosSubcriteriosFull(){
        return repository.obtenerSubcirteriosConCantidadIndicadorFull();
    }

    @Override
    public List<SubcriterioIndicadoresProjection> obtenerSubcriterios(Long id_criterio, Long id_modelo) {
        return repository.obtenerSubcriterios(id_criterio, id_modelo);
    }

    ;

}
