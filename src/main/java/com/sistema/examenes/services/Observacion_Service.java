package com.sistema.examenes.services;

import com.sistema.examenes.entity.Observacion;

import java.util.List;

public interface Observacion_Service extends GenericService<Observacion, Long>{
    public List<Observacion> listar() ;
    List<Observacion> observacionUsuario(String user, Long id);
    public void eliminar(Long id);
    public List<Observacion> listarObservacActivi(Long id);
    List<Observacion> observacionactividad(Long id_actividad);
}
