package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Evidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Evidencia_repository extends JpaRepository<Evidencia, Long> {
    @Query(value = "SELECT * from evidencia where visible =true", nativeQuery = true)
    List<Evidencia> listarEvidencia();

    @Query(value = "SELECT * from evidencia e JOIN asignacion_evidencia ae ON ae.evidencia_id_evidencia = e.id_evidencia " +
            "JOIN usuarios u ON ae.usuario_id = u.id where u.username=:username and e.visible =true AND ae.id_modelo=(SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    public List<Evidencia> evidenciaUsuario(String username);

   /* @Query(value = " SELECT e.* FROM evidencia e  \n" +
            "               LEFT JOIN asignacion_evidencia ae ON e.id_evidencia = ae.evidencia_id_evidencia \n" +
            "               WHERE ae.id_asignacion_evidencia IS NULL AND e.visible=true",nativeQuery = true)*/

    @Query(value = "SELECT e.* FROM evidencia e " +
            "JOIN indicador i ON i.id_indicador = e.indicador_id_indicador " +
            "JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio " +
            "JOIN criterio cri ON cri.id_criterio = s.id_criterio " +
            "LEFT JOIN asignacion_evidencia ae ON ae.evidencia_id_evidencia = e.id_evidencia AND ae.visible = true " +
            "LEFT JOIN asignacion_admin aa ON aa.criterio_id_criterio = cri.id_criterio AND aa.visible = true " +
            "WHERE aa.id_modelo = (SELECT MAX(id_modelo) FROM modelo) AND aa.usuario_id =:idUser " +
            "AND (e.id_evidencia IS NULL OR e.id_evidencia NOT IN (SELECT evidencia_id_evidencia FROM asignacion_evidencia WHERE visible = true)) " +
            "AND e.visible = true ORDER BY e.id_evidencia;", nativeQuery = true)
    List<Evidencia> listarEvidenciaAsigna(Long idUser);

    @Query(value = "SELECT DISTINCT e.id_evidencia, e.*, c.id_criterio FROM evidencia e " +
            "JOIN indicador i ON e.indicador_id_indicador = i.id_indicador " +
            "JOIN subcriterio sc ON i.subcriterio_id_subcriterio = sc.id_subcriterio " +
            "JOIN criterio c ON sc.id_criterio = c.id_criterio " +
            "JOIN asignacion_admin aa ON c.id_criterio = aa.criterio_id_criterio " +
            "WHERE c.id_criterio=:idcriterio AND e.visible = true " +
            "AND e.id_evidencia NOT IN (SELECT evidencia_id_evidencia FROM asignacion_evidencia  WHERE visible=true) " +
            "ORDER BY c.id_criterio,e.id_evidencia", nativeQuery = true)
    List<Evidencia> evidenciacriterio(Long idcriterio);


    // SELECT evidencia.*
    // FROM public.indicador join public.evidencia ON
    // evidencia.indicador_id_indicador = indicador.id_indicador
    // WHERE evidencia.indicador_id_indicador=6 And evidencia.visible=true;
    @Query(value = "SELECT evidencia.* FROM public.indicador join public.evidencia ON evidencia.indicador_id_indicador = indicador.id_indicador WHERE evidencia.indicador_id_indicador=:id_indicador And evidencia.visible=true", nativeQuery = true)
    List<Evidencia> listarEvidenciaPorIndicador(Long id_indicador);
    
}
