package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Asignacion_Admin;
import com.sistema.examenes.projection.AsignacionProjection;
import com.sistema.examenes.projection.NombreAsigProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface Asignacion_Admin_repository extends JpaRepository<Asignacion_Admin, Long> {
    @Query(value = "SELECT * from asignacion_admin where visible =true", nativeQuery = true)
    List<Asignacion_Admin> listarAsignacion_Admin();
    @Query(value = "SELECT DISTINCT u.id as enc, per.primer_nombre||' '||per.primer_apellido as nombrescri, cri.nombre as actividasi " +
            "FROM asignacion_admin aa JOIN usuarios u ON aa.usuario_id=u.id " +
            "JOIN persona per ON per.id_persona=u.persona_id_persona AND u.visible=true " +
            "JOIN criterio cri ON cri.id_criterio=aa.criterio_id_criterio AND cri.visible=true " +
            "JOIN subcriterio s ON s.id_criterio=cri.id_criterio AND s.visible=true " +
            "JOIN indicador i ON i.subcriterio_id_subcriterio= s.id_subcriterio AND i.visible=true " +
            "JOIN asignacion_indicador ai ON ai.indicador_id_indicador=i.id_indicador AND ai.visible=true " +
            "JOIN modelo mo ON mo.id_modelo=ai.modelo_id_modelo " +
            "WHERE aa.visible=CAST(:veri AS BOOLEAN) AND aa.id_modelo=:id_modelo ORDER BY u.id;", nativeQuery = true)
    List<AsignacionProjection> asignacionAdmin(Long id_modelo, String veri);

    @Query(value = "SELECT * from asignacion_admin where usuario_id = ?1 and id_modelo = ?2 and visible =true ", nativeQuery = true)
    Asignacion_Admin listarAsignacion_AdminPorUsuario(Long id_usuario,Long id_modelo);
    @Query(value = "SELECT * from asignacion_admin where criterio_id_criterio = ?1 and id_modelo = ?2 AND visible=true", nativeQuery = true)
    Asignacion_Admin listarAsignacion_AdminPorUsuarioCriterio(Long id_criterio, Long id_modelo);

    @Query(value = "SELECT * from asignacion_admin where criterio_id_criterio = ?1 and id_modelo = ?2 and usuario_id = ?3", nativeQuery = true)
    Asignacion_Admin asignacion_existente(Long id_criterio, Long id_modelo,Long id_usuario);
    @Query(value = "SELECT per.primer_nombre ||' '|| per.segundo_nombre||' '||per.primer_apellido||' '||per.segundo_apellido AS nombreaa FROM persona per\n" +
            "JOIN usuarios u ON u.persona_id_persona=per.id_persona\n" +
            "JOIN asignacion_admin aa ON aa.usuario_id=u.id AND aa.visible=true\n" +
            "JOIN modelo mo ON mo.id_modelo=aa.id_modelo\n" +
            "WHERE aa.id_modelo=:id_modelo AND aa.criterio_id_criterio=:id_criterio", nativeQuery = true)
    NombreAsigProjection listarnombre_Admin(Long id_modelo, Long id_criterio);
}
