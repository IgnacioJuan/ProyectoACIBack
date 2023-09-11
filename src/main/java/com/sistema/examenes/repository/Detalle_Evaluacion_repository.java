package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Detalle_Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Detalle_Evaluacion_repository extends JpaRepository<Detalle_Evaluacion, Long> {
    @Query(value = "SELECT * from detalle_evaluacion where visible =true",nativeQuery = true)
    List<Detalle_Evaluacion> listarDetalleEvaluacion();

    @Query(value = "    SELECT * FROM detalle_evaluacion\n" +
            "    WHERE visible = true AND evidencia_id_evidencia = :idEvidencia ",nativeQuery = true)
    List<Detalle_Evaluacion> listarDetalleEvaluacion( Long idEvidencia);

    @Query(value = "SELECT CASE  WHEN COUNT(id_detalle_evaluacion)>0 THEN true ELSE false END FROM detalle_evaluacion " +
            "WHERE evidencia_id_evidencia=:id_evidencia AND usuario_id=:id AND id_modelo=:id_modelo",nativeQuery = true)
    Boolean existeeva(Long id_evidencia,Long id,Long id_modelo);

    @Query(value = "SELECT id_detalle_evaluacion FROM detalle_evaluacion " +
            "WHERE evidencia_id_evidencia=:id_evidencia AND usuario_id=:id AND id_modelo=:id_modelo AND estado=true",nativeQuery = true)
    Long iddetalle(Long id_evidencia,Long id,Long id_modelo);

}
