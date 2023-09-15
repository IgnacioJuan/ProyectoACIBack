package com.sistema.examenes.repository;

import com.sistema.examenes.entity.UsuarioRol;
import com.sistema.examenes.projection.CriteUsuarioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol,Long> {

    @Query("SELECT ur FROM UsuarioRol ur JOIN FETCH ur.usuario u WHERE u.visible = true")
    List<UsuarioRol> listarv();

    UsuarioRol findByUsuario_Id(Long usuarioId);
    @Query("SELECT CASE WHEN aa.criterio_id_criterio IS NOT NULL THEN " +
            "ROW_NUMBER() OVER (ORDER BY cri.id_criterio) || '. ' || cri.nombre " +
            "ELSE 'No asignado' END AS ncriterio FROM asignacion_admin aa " +
            "LEFT JOIN criterio cri ON cri.id_criterio = aa.criterio_id_criterio AND aa.visible = true " +
            "WHERE aa.usuario_id =:id AND aa.id_modelo =:id_modelo ORDER BY criterio")
    List<CriteUsuarioProjection> actividadesusuario(Long id, Long id_modelo);

    @Query("SELECT CASE WHEN ae.evidencia_id_evidencia IS NOT NULL THEN " +
            "ROW_NUMBER() OVER (ORDER BY e.id_evidencia) || '. ' || e.descripcion " +
            "ELSE 'No asignado' END AS ncriterio FROM asignacion_evidencia ae " +
            "LEFT JOIN evidencia e ON e.id_evidencia = ae.evidencia_id_evidencia AND ae.visible = true " +
            "WHERE ae.usuario_id =:id AND ae.id_modelo =:id_modelo ORDER BY ncriterio ")
    List<CriteUsuarioProjection> evidenusuario(Long id, Long id_modelo);
}
