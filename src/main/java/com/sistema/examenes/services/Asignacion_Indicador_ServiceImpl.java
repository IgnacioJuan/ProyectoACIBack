package com.sistema.examenes.services;

import com.sistema.examenes.entity.Asignacion_Indicador;
import com.sistema.examenes.entity.Modelo;
import com.sistema.examenes.repository.Asignacion_Indicador_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Asignacion_Indicador_ServiceImpl extends GenericServiceImpl<Asignacion_Indicador, Long>
        implements Asignacion_Indicador_Service {
    @Autowired
    private Asignacion_Indicador_repository repository;

    @Override
    public CrudRepository<Asignacion_Indicador, Long> getDao() {
        return repository;
    }

    @Override
    public List<Asignacion_Indicador> listar() {
        return repository.listarAsignacionIndicador();
    }

    @Override
    public List<Asignacion_Indicador> findByModelo(Modelo modelo) {
        return repository.findByModelo(modelo);
    }

    @Override
    public List<Asignacion_Indicador> listarAsignacion(Long id_modelo) {
        return repository.listarAsignacion(id_modelo);
    }

    @Override
    public void eliminarasignacion(Long id) {
        repository.eliminarasignacion(id);
    }
}
