package com.sistema.examenes.services;

import com.sistema.examenes.entity.Ponderacion;
import com.sistema.examenes.projection.PonderacionProjection;

import java.util.Date;
import java.util.List;

public interface Ponderacion_Service extends GenericService<Ponderacion, Long> {
    public List<Ponderacion> listar();

    public List<Ponderacion> listarPonderacionPorModelo(Long id_modelo);
    public List<PonderacionProjection> listarPonderacionModelo(Long id_modelo);
    public List<Ponderacion> listarPonderacionPorFecha(String fecha,Long contador);

    public List<Ponderacion> listarPorFecha(String fecha);
    public List<PonderacionProjection> idmax(Long id_modelo);
    public void eliminarPonderacion(Long contador, String fecha);
}
