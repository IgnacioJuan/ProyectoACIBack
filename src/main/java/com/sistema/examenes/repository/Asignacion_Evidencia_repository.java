package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Asignacion_Evidencia;
import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.projection.AsignacionEvidenciaProyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Asignacion_Evidencia_repository extends JpaRepository<Asignacion_Evidencia, Long> {
    @Query(value = "SELECT ae.* from asignacion_evidencia ae JOIN evidencia e ON e.id_evidencia=ae.evidencia_id_evidencia AND ae.visible =true\n" +
            "JOIN indicador i ON e.indicador_id_indicador = i.id_indicador\n" +
            "JOIN asignacion_indicador po ON i.id_indicador = po.indicador_id_indicador\n" +
            "JOIN modelo mo ON mo.id_modelo=po.modelo_id_modelo\n" +
            "AND mo.id_modelo = (SELECT MAX(id_modelo) FROM modelo) \n" +
            "WHERE ae.visible=true ORDER BY ae.usuario_id,ae.evidencia_id_evidencia;",nativeQuery = true)
    List<Asignacion_Evidencia> listarAsignacionEvidencia();

    @Query(value = "SELECT ae.id_asignacion_evidencia as idAsignacionEvidencia, ae.usuario_id as usuarioId, ae.evidencia_id_evidencia as evidenciaId " +
            "FROM asignacion_evidencia ae " +
            "JOIN evidencia e ON e.id_evidencia = ae.evidencia_id_evidencia AND ae.visible = true " +
            "JOIN indicador i ON e.indicador_id_indicador = i.id_indicador " +
            "JOIN asignacion_indicador po ON i.id_indicador = po.indicador_id_indicador " +
            "JOIN modelo mo ON mo.id_modelo = po.modelo_id_modelo " +
            "AND mo.id_modelo = (SELECT MAX(id_modelo) FROM modelo) " +
            "WHERE ae.visible = true " +
            "ORDER BY ae.usuario_id, ae.evidencia_id_evidencia;", nativeQuery = true)
    List<AsignacionEvidenciaProyeccion> listarAsignacionEvidenciaProyeccion();
    @Query(value =
            "SELECT asignacion_evidencia.id_asignacion_evidencia, evidencia.id_evidencia, evidencia.descripcion, evidencia.nombre\n" +
                    "FROM asignacion_evidencia, evidencia, usuarios\n" +
                    "WHERE asignacion_evidencia.evidencia_id_evidencia = evidencia.id_evidencia\n" +
                    "AND asignacion_evidencia.usuario_id = usuarios.id\n" +
                    "AND evidencia.visible = true\n" +
                    "AND usuarios.username= :usuario", nativeQuery = true)
    List<Asignacion_Evidencia> listarporAsignacionUsuario(@Param("usuario") String usuario);
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM asignacion_evidencia ae " +
                    "JOIN evidencia e ON e.id_evidencia=ae.evidencia_id_evidencia AND ae.visible=true AND e.visible=true " +
                    "JOIN indicador i ON i.id_indicador=e.indicador_id_indicador " +
                    "JOIN subcriterio s ON s.id_subcriterio=i.subcriterio_id_subcriterio " +
                    "JOIN criterio cri ON cri.id_criterio = s.id_criterio " +
                    "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
                    "WHERE aa.usuario_id=:id_usuario " +
                    "AND ae.evidencia_id_evidencia=:id_evidencia " +
                    "AND aa.id_modelo=:id_modelo", nativeQuery = true)
    Boolean verificarAsignacionUsuario(Long id_usuario, Long id_evidencia,Long id_modelo);
}
