package com.sistema.examenes.services;

import com.sistema.examenes.entity.Archivo_s;
import com.sistema.examenes.entity.Evidencia;
import com.sistema.examenes.projection.ArchivoProjection;
import com.sistema.examenes.repository.Archivo_repository;
import com.sistema.examenes.repository.Evidencia_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Archivo_ServiceImpl extends GenericServiceImpl<Archivo_s, Long> implements Archivo_Service {
    @Autowired
    private Archivo_repository repository;
    @Override
    public CrudRepository<Archivo_s, Long > getDao() {
        return repository;
    }

    @Override
    public List<Archivo_s> listar() {
        return repository.listararchivo();
    }

    @Override
    public List<Archivo_s> listararchivouser(String username) {
        return repository.listararchivouser(username);
    }

    @Override
    public List<Archivo_s> listararchivoActividad(Long idActividad) {
        return repository.listararchivoActividad(idActividad);
    }

    @Override
    public List<ArchivoProjection> listararchi() {
        return repository.listararchi();
    }


}
