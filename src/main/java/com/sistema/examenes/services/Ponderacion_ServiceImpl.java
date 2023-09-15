package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Ponderacion;
import com.sistema.examenes.projection.PonderacionProjection;
import com.sistema.examenes.repository.Ponderacion_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Ponderacion_ServiceImpl extends GenericServiceImpl<Ponderacion, Long> implements Ponderacion_Service {
    @Autowired
    private Ponderacion_repository repository;

    @Override
    public CrudRepository<Ponderacion, Long> getDao() {
        return repository;
    }

    @Override
    public List<Ponderacion> listar() {
        return repository.listarPonderacion();
    }

    @Override
    public List<Ponderacion> listarPorFecha(String fecha) {
        return repository.listarPorFecha(fecha);
    }

    @Override
    public List<PonderacionProjection> idmax(Long id_modelo) {
        return repository.idmax(id_modelo);
    }

    @Override
    public void eliminarPonderacion(Long contador,String fecha) {
        repository.eliminarPonderacion(contador, fecha);
    }

    @Override
    public List<Ponderacion> listarPonderacionPorModelo(Long id_modelo) {
        return repository.listarPonderacionPorModelo(id_modelo);
    }

    @Override
    public List<PonderacionProjection> listarPonderacionModelo(Long id_modelo) {
        return repository.listarPonderacionModelo(id_modelo);
    }

    @Override
    public List<Ponderacion> listarPonderacionPorFecha(String fecha,Long contador) {
        return repository.listarPonderacionPorFecha(fecha, contador);
    }
    @Override
    public Ponderacion save(Ponderacion entity) {
        return repository.save(entity);
    }
}
