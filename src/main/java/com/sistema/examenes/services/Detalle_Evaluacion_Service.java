package com.sistema.examenes.services;

import com.sistema.examenes.entity.Detalle_Evaluacion;

import java.util.List;

public interface Detalle_Evaluacion_Service extends GenericService<Detalle_Evaluacion, Long>{
    public List<Detalle_Evaluacion> listar() ;
    public List<Detalle_Evaluacion> listarDetalleEvaluacion( Long idEvidencia);
    Boolean existeeva(Long id_evidencia,Long id,Long id_modelo);
    Long iddetalle(Long id_evidencia,Long id,Long id_modelo);
}
