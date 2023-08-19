package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Modelo;
import com.sistema.examenes.projection.ModelIndiProjection;
import com.sistema.examenes.projection.ModeloVistaProjection;
import com.sistema.examenes.repository.Modelo_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Modelo_ServiceImpl extends GenericServiceImpl<Modelo, Long> implements Modelo_Service {

    @Autowired
    private Modelo_repository repository;

    @Override
    public CrudRepository<Modelo, Long> getDao() {
        return repository;
    }

    @Override
    public List<Modelo> listar() {
        return repository.listarModelo();
    }

    @Override
    public Modelo listarMaximo() {
        return repository.listarModeloMaximo();
    }

    @Override
    public List<Modelo> listarModeloExcepto(Long id_modelo) {
        return repository.listarModeloExcepto(id_modelo);
    }
    @Override
    public List<ModeloVistaProjection> obtenerDatosModelo(){
        return repository.obtenerModeloVista();
    }

    @Override
    public List<ModelIndiProjection> listindiModelo(Long id_modelo) {
        return repository.listindiModelo(id_modelo);
    }

    ;
}
