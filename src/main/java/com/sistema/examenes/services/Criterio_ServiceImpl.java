package com.sistema.examenes.services;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.projection.*;
import com.sistema.examenes.repository.Criterio_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Criterio_ServiceImpl extends GenericServiceImpl<Criterio, Long> implements Criterio_Service {

    @Autowired
    private Criterio_repository repository;

    @Override
    public CrudRepository<Criterio, Long> getDao() {
        return repository;
    }

    @Override
    public List<Criterio> listar() {
        return repository.listarCriterio();
    }

    @Override
    public List<Criterio> obtenerCriterios() {
        return repository.obtenerCriterios();
    }

    @Override
    public List<Criterio> obtenerCriteriosUltimoModelo() {
        return repository.obtenerCriteriosUltimoModelo();
    }

    @Override
    public List<Criterio> obtenerCriterioModelo() {
        return repository.obtenerCriteriosModelo();
    }

    @Override
    public List<Criterio> obtenerCriterioIdModelo(Long id) {
        return repository.obtenerCriteriosModeloId(id);
    }

    @Override
    public List<Criterio> listarCriterioPorIndicador(Long id_indicador) {
        return repository.listarCriterioPorIndicador(id_indicador);
    }
    public List<CriterioSubcriteriosProjection> obtenerDatosCriterios() {
        return repository.obtenerCriteriosConCantidadSubcriterios();
    }

    @Override
    public List<ValoresProjection> listarvalores(Long id_modelo) {
        return repository.listarvalores(id_modelo);
    }

    @Override
    public List<ValoresProjection> valoresporcriterio(Long id_modelo, String nombre) {
        return repository.valoresporcriterio(id_modelo, nombre);
    }

    @Override
    public List<ValoresProjection> listarvaladmin(Long id_modelo, Long id) {
        return repository.listarvaladmin(id_modelo,id);
    }

    @Override
    public IdCriterioProjection idcriterio(String nombre) {
        return repository.idcriterio(nombre);
    }

    @Override
    public List<CriteProjection> actividadesusuario(Long id, Long id_modelo) {
        return repository.actividadesusuario(id, id_modelo);
    }

    @Override
    public List<ValoresProjection> listarvalresp(Long id_modelo, Long id) {
        return repository.listarvalresp(id_modelo, id);
    }

    @Override
    public CorreoProjection getCorreo(Long id_modelo, Long id_evidencia) {
        return repository.getCorreo(id_modelo, id_evidencia);
    }

}
