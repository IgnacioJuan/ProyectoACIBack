package com.sistema.examenes.services;

import com.sistema.examenes.entity.Asignacion_Admin;
import com.sistema.examenes.projection.AsignacionProjection;

import java.util.List;

public interface Asignacion_Admin_Service extends GenericService<Asignacion_Admin, Long> {
    public List<Asignacion_Admin> listar();

    public Asignacion_Admin listarAsignacion_AdminPorUsuario(Long id_usuario,Long id_modelo);

    public Asignacion_Admin listarAsignacion_AdminPorUsuarioCriterio(Long id_criterio, Long id_modelo);
    public List<AsignacionProjection> asignacionAdmin(Long id_modelo, String veri);
    public Asignacion_Admin asignacion_existente(Long id_criterio, Long id_modelo,Long id_usuario);

}
