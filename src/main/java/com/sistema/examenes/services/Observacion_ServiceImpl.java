package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Observacion;
import com.sistema.examenes.repository.Observacion_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class Observacion_ServiceImpl extends GenericServiceImpl<Observacion, Long> implements Observacion_Service {
    @Autowired
    private Observacion_repository repository;
    @Override
    public CrudRepository<Observacion, Long > getDao() {
        return repository;
    }
    @Override
    public List<Observacion> listar() {
        return repository.listarObservacion();
    }

    @Override
    public List<Observacion> observacionUsuario(String user, Long id) {
        return repository.observacionUsuario(user, id);
    }

    @Transactional
    public void eliminar(Long id) {
        repository.borrar(id);
    }

    @Override
    public List<Observacion> listarObservacActivi(Long id) {
        return repository.observacionActividad(id);
    }

    @Override
    public List<Observacion> observacionactividad(Long id_actividad) {
        return repository.observacionactividad(id_actividad);
    }

}