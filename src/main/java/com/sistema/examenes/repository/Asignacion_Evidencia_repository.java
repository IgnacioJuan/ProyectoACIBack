package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Asignacion_Evidencia;
import com.sistema.examenes.entity.Criterio;
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

    @Query(value =
            "SELECT asignacion_evidencia.id_asignacion_evidencia, evidencia.id_evidencia, evidencia.descripcion, evidencia.nombre\n" +
                    "FROM asignacion_evidencia, evidencia, usuarios\n" +
                    "WHERE asignacion_evidencia.evidencia_id_evidencia = evidencia.id_evidencia\n" +
                    "AND asignacion_evidencia.usuario_id = usuarios.id\n" +
                    "AND evidencia.visible = true\n" +
                    "AND usuarios.username= :usuario", nativeQuery = true)
    List<Asignacion_Evidencia> listarporAsignacionUsuario(@Param("usuario") String usuario);
}
