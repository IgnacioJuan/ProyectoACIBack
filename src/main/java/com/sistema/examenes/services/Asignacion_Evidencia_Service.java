package com.sistema.examenes.services;

import com.sistema.examenes.entity.Asignacion_Evidencia;
import com.sistema.examenes.projection.AsignacionEvidenciaProyeccion;

import java.util.List;

public interface Asignacion_Evidencia_Service extends GenericService<Asignacion_Evidencia, Long>{
    public List<Asignacion_Evidencia> listar() ;
    public List<Asignacion_Evidencia> listarporUsuario (String usuario  ) ;

    List<AsignacionEvidenciaProyeccion> listarAsignacionEvidenciaProyeccion();
    Boolean verificarAsignacionUsuario(Long id_usuario, Long id_evidencia,Long id_modelo);
}
