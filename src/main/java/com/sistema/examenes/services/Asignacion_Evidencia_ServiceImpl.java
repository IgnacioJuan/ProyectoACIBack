package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Asignacion_Evidencia;
import com.sistema.examenes.projection.AsignaProjection;
import com.sistema.examenes.projection.AsignacionEvidenciaProyeccion;
import com.sistema.examenes.repository.Asignacion_Evidencia_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Asignacion_Evidencia_ServiceImpl extends GenericServiceImpl<Asignacion_Evidencia, Long> implements Asignacion_Evidencia_Service {
    @Autowired
    private Asignacion_Evidencia_repository repository;
    @Override
    public CrudRepository<Asignacion_Evidencia, Long > getDao() {
        return repository;
    }
    @Override
    public List<Asignacion_Evidencia> listarporUsuario(String usuario) {
        return repository.listarporAsignacionUsuario (usuario);
    }

    @Override
    public List<AsignacionEvidenciaProyeccion> listarAsignacionEvidenciaProyeccion() {
        return repository.listarAsignacionEvidenciaProyeccion();
    }

    @Override
    public Boolean verificarAsignacionUsuario(Long id_usuario, Long id_evidencia, Long id_modelo) {
        return repository.verificarAsignacionUsuario(id_usuario,id_evidencia,id_modelo);
    }

    @Override
    public List<AsignaProjection> listarAsigEvidencia() {
        return repository.listarAsigEvidencia();
    }

    @Override
    public List<Asignacion_Evidencia> listar() {
        return repository.listarAsignacionEvidencia();
    }
}

