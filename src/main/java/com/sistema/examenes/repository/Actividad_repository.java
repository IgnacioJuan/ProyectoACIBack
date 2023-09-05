package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.projection.ActivAprobadaProjection;
import com.sistema.examenes.projection.ActividadesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Actividad_repository extends JpaRepository<Actividad, Long> {

    @Query(value = "SELECT ac.* FROM actividad ac " +
            "JOIN evidencia e ON e.id_evidencia=ac.id_evidencia AND ac.visible=true " +
            "JOIN asignacion_indicador ai ON ai.indicador_id_indicador=e.indicador_id_indicador " +
            "JOIN modelo mo ON mo.id_modelo=ai.modelo_id_modelo " +
            "WHERE ac.fecha_inicio>= mo.fecha_inicio AND ac.fecha_fin<=mo.fecha_fin " +
            "AND ai.modelo_id_modelo=(SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    List<Actividad> listarActividad();


    @Query(value = "SELECT ac.*\n"
            + "FROM actividad ac \n"
            + "JOIN evidencia e ON ac.id_evidencia = e.id_evidencia\n"
            + "JOIN indicador i ON e.indicador_id_indicador = i.id_indicador\n"
            + "JOIN asignacion_indicador ag ON ag.indicador_id_indicador = i.id_indicador\n"
            + "LEFT JOIN ponderacion po ON i.id_indicador = po.indicador_id_indicador\n"
            + "WHERE po.indicador_id_indicador IS NULL\n"
            + "AND ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo)\n"
            + "AND ac.fecha_fin < CURRENT_DATE;", nativeQuery = true)
    List<Actividad> listarActividadAtrasadas();

    @Query(value = "SELECT ac.*\n" +
"FROM actividad ac JOIN evidencia e\n" +
"ON ac.id_evidencia = e.id_evidencia\n" +
"JOIN indicador i\n" +
"ON e.indicador_id_indicador = i.id_indicador\n" +
"JOIN asignacion_indicador ag\n" +
"ON ag.indicador_id_indicador = i.id_indicador\n" +
"WHERE ac.estado = 'Aprobada'\n" +
"AND ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    List<Actividad> listarActividadCumplidas();
    
    @Query(value = "SELECT ac.*\n" +
"FROM actividad ac JOIN evidencia e\n" +
"ON ac.id_evidencia = e.id_evidencia\n" +
"JOIN indicador i\n" +
"ON e.indicador_id_indicador = i.id_indicador\n" +
"JOIN asignacion_indicador ag\n" +
"ON ag.indicador_id_indicador = i.id_indicador\n" +
"WHERE ac.estado = 'Rechazada'\n" +
"AND ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    List<Actividad> listarEvideRechazadasFecha();
    
@Query(value = "SELECT pe.primer_nombre||' '||pe.primer_apellido as encargado, ac.nombre as actividades, ac.fecha_inicio as inicio, " +
        "ac.fecha_fin as fin, ar.enlace " +
        "FROM actividad ac JOIN evidencia ev ON ac.id_evidencia=ev.id_evidencia AND ac.visible=true " +
        "JOIN indicador i ON i.id_indicador = ev.indicador_id_indicador AND i.visible=true " +
        "JOIN asignacion_indicador ai ON ai.indicador_id_indicador=i.id_indicador AND ai.visible=true AND ai.modelo_id_modelo=:id_modelo " +
        "JOIN modelo mo ON mo.id_modelo=ai.modelo_id_modelo JOIN usuarios u ON u.id=ac.usuario_id " +
        "LEFT JOIN archivo ar ON ar.id_actividad = ac.id_actividad AND ar.visible = true " +
        "JOIN persona pe ON pe.id_persona=u.persona_id_persona WHERE ac.fecha_inicio BETWEEN mo.fecha_inicio " +
        "AND mo.fecha_fin AND ac.fecha_fin  BETWEEN mo.fecha_inicio AND mo.fecha_fin " +
        "AND ac.estado != 'Aprobada' AND ac.visible=true ", nativeQuery = true)
List<ActivAprobadaProjection> actividadRechazada(Long id_modelo);

    @Query(value = "SELECT per.primer_nombre || ' ' || per.primer_apellido as nombres, COUNT(ac.id_actividad) " +
            "as total, ROUND(SUM(CASE WHEN ac.estado = 'Aprobada' THEN 1 ELSE 0 END) * 100.0 / COUNT(ac.id_actividad), 2) as avance " +
            "FROM actividad ac JOIN evidencia ev ON ac.id_evidencia = ev.id_evidencia " +
            "JOIN indicador i ON i.id_indicador = ev.indicador_id_indicador " +
            "JOIN asignacion_indicador po ON po.indicador_id_indicador = i.id_indicador " +
            "JOIN modelo mo ON mo.id_modelo = po.modelo_id_modelo " +
            "JOIN usuarios u ON u.id = ac.usuario_id " +
            "JOIN persona per ON u.persona_id_persona = per.id_persona " +
            "WHERE mo.id_modelo =:id_modelo AND ac.fecha_inicio BETWEEN mo.fecha_inicio " +
            "AND mo.fecha_fin AND ac.fecha_fin  BETWEEN mo.fecha_inicio AND mo.fecha_fin " +
            "AND ac.visible = true " +
            "GROUP BY per.primer_nombre, per.primer_apellido;", nativeQuery = true)
    List<ActividadesProjection> actividadCont(Long id_modelo);

    @Query(value = "SELECT * FROM actividad ac JOIN evidencia ev ON ac.id_evidencia=ev.id_evidencia " +
            "JOIN indicador i ON i.id_indicador = ev.indicador_id_indicador " +
            "JOIN ponderacion po ON po.indicador_id_indicador=i.id_indicador " +
            "JOIN modelo mo ON mo.id_modelo=po.modelo_id_modelo WHERE " +
            "mo.id_modelo=(SELECT MAX(id_modelo) FROM modelo) " +
            "AND ac.visible=true AND ac.usuario_id=:id;", nativeQuery = true)
    List<Actividad> actividadUsu(Long id);

    @Query(value = "SELECT pe.primer_nombre||' '||pe.primer_apellido as encargado, ac.nombre as actividades, ac.fecha_inicio as inicio, " +
            "ac.fecha_fin as fin, ar.enlace " +
            "FROM actividad ac JOIN evidencia ev ON ac.id_evidencia=ev.id_evidencia AND ac.visible=true " +
            "JOIN indicador i ON i.id_indicador = ev.indicador_id_indicador AND i.visible=true " +
            "JOIN asignacion_indicador ai ON ai.indicador_id_indicador=i.id_indicador AND ai.visible=true AND ai.modelo_id_modelo=:id_modelo " +
            "JOIN modelo mo ON mo.id_modelo=ai.modelo_id_modelo JOIN usuarios u ON u.id=ac.usuario_id " +
            "LEFT JOIN archivo ar ON ar.id_actividad = ac.id_actividad AND ar.visible = true " +
            "JOIN persona pe ON pe.id_persona=u.persona_id_persona WHERE ac.fecha_inicio BETWEEN mo.fecha_inicio " +
            "AND mo.fecha_fin AND ac.fecha_fin  BETWEEN mo.fecha_inicio AND mo.fecha_fin " +
            "AND ac.estado = 'Aprobada' AND ac.visible=true ;", nativeQuery = true)
    List<ActivAprobadaProjection> actividadAprobada(Long id_modelo);
    @Query(value = "select * from  actividad ac JOIN usuarios u ON ac.usuario_id = u.id where u.username=:username and ac.visible =true",nativeQuery = true)
    List<Actividad>listarporusuario(String username);
    List<Actividad> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = "SELECT * FROM actividad WHERE visible= true AND id_evidencia=:idEvidendicia ;",nativeQuery = true)
    List<Actividad>listarporEvidencia(Long idEvidendicia);

    @Query(value = "SELECT * FROM actividad WHERE usuario_id = :idUsuario ;",nativeQuery = true)
    List<Actividad>listarByUsuario(Long idUsuario);

}
