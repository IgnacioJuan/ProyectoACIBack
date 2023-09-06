package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Modelo;
import com.sistema.examenes.projection.IndicadorEvidenciasProjectionFull;
import com.sistema.examenes.projection.ModelIndiProjection;
import com.sistema.examenes.projection.ModeloVistaProjection;
import com.sistema.examenes.projection.criteriosdesprojection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Modelo_repository extends JpaRepository<Modelo, Long> {
    @Query(value = "SELECT * from modelo order by id_modelo desc", nativeQuery = true)
    List<Modelo> listarModelo();

    @Query(value = "SELECT * FROM modelo WHERE id_modelo = (SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    public Modelo listarModeloMaximo();

    // SELECT id_modelo, fecha_fin, fecha_final_act, fecha_inicio, nombre, visible,
    // usuario_id
    // FROM public.modelo m
    // where m.visible=true and m.id_modelo!=:id_modelo;

    @Query(value = "SELECT * from modelo where visible =true and id_modelo!=:id_modelo", nativeQuery = true)
    List<Modelo> listarModeloExcepto(Long id_modelo);


    @Query(value = "SELECT m.id_modelo as id_modelo, " +
            "m.nombre as nombre, " +
            "m.fecha_fin as fecha_fin, " +
            "m.fecha_final_act as fecha_final_act, " +
            "m.fecha_inicio as fecha_inicio, " +
            "(SELECT COUNT(*) FROM asignacion_indicador ai WHERE ai.modelo_id_modelo = m.id_modelo AND ai.visible = true) AS nro_indicadores, " +
            "COUNT(DISTINCT ic.subcriterio_id_subcriterio) AS nro_subcriterios, " +
            "COUNT(DISTINCT sc.id_criterio) AS nro_criterios " +
            "FROM modelo m " +
            "JOIN asignacion_indicador ai ON ai.modelo_id_modelo = m.id_modelo " +
            "JOIN indicador ic ON ic.id_indicador = ai.indicador_id_indicador " +
            "JOIN subcriterio sc ON ic.subcriterio_id_subcriterio = sc.id_subcriterio " +
            "WHERE ai.visible  = true AND ic.visible = true " +
            "GROUP by m.id_modelo " +
            "order by m.id_modelo desc", nativeQuery = true)
    List<ModeloVistaProjection> obtenerModeloVista();
    @Query(value = "SELECT cri.nombre AS crite, sub.nombre AS sub, i.id_indicador AS id_indi, i.nombre AS ind_nombre, " +
            "CASE WHEN ai.visible IS NOT NULL THEN ai.visible ELSE false END AS visi " +
            "FROM criterio cri JOIN subcriterio sub ON sub.id_criterio = cri.id_criterio AND cri.visible=true " +
            "JOIN indicador i ON i.subcriterio_id_subcriterio = sub.id_subcriterio AND sub.visible=true " +
            "LEFT JOIN asignacion_indicador ai ON ai.indicador_id_indicador = i.id_indicador AND i.visible=true " +
            "AND ai.modelo_id_modelo =:id_modelo ORDER BY cri.id_criterio,sub.id_subcriterio,i.id_indicador;", nativeQuery = true)
    List<ModelIndiProjection> listindiModelo(Long id_modelo);

    @Query(value = "SELECT cri.nombre AS criterionomj, sub.nombre AS subcrierioj, i.id_indicador AS id_indicardorj, " +
            "i.nombre AS ind_nombrej, ev.descripcion AS descrip, " +
            "CASE WHEN ai.visible IS NOT NULL THEN ai.visible ELSE false END AS visi, " +
            "arc.nombre AS archivo_nombre, " +
            "arc.enlace AS archivo_enlace " +
            "FROM criterio cri JOIN subcriterio sub ON cri.id_criterio = sub.id_criterio AND sub.visible = true " +
            "LEFT JOIN indicador i ON sub.id_subcriterio = i.subcriterio_id_subcriterio AND i.visible = true " +
            "LEFT JOIN asignacion_indicador ai ON i.id_indicador = ai.indicador_id_indicador " +
            "LEFT JOIN evidencia ev ON i.id_indicador = ev.indicador_id_indicador AND ev.visible = true " +
            "LEFT JOIN actividad ac ON ev.id_evidencia = ac.id_evidencia AND ac.visible = true " +
            "LEFT JOIN archivo arc ON ac.id_actividad = arc.id_actividad AND arc.visible = true " +
            "WHERE ai.modelo_id_modelo =:id_modelo AND cri.nombre =:nombre " +
            "ORDER BY cri.id_criterio, sub.id_subcriterio, i.id_indicador;", nativeQuery = true)
    List<criteriosdesprojection> listicritedes(Long id_modelo,String nombre);


@Query(value = "SELECT cri.nombre AS criterionomj,sub.nombre AS subcrierioj,i.id_indicador AS id_indicardorj, " +
        "i.nombre AS ind_nombrej,CASE WHEN ai.visible IS NOT NULL THEN ai.visible ELSE false END AS visi, " +
        "arc.nombre AS archivo_nombre,arc.enlace AS archivo_enlace " +
        "FROM archivo arc JOIN actividad ac ON ac.id_actividad = arc.id_actividad " +
        "JOIN evidencia ev ON ev.id_evidencia = ac.id_evidencia " +
        "JOIN indicador i ON ev.indicador_id_indicador = i.id_indicador " +
        "JOIN subcriterio sub ON i.subcriterio_id_subcriterio = sub.id_subcriterio AND sub.visible = true " +
        "JOIN criterio cri ON cri.id_criterio = sub.id_criterio AND sub.visible = true " +
        "LEFT JOIN asignacion_indicador ai ON ai.indicador_id_indicador = i.id_indicador AND i.visible = true " +
        "WHERE ai.modelo_id_modelo =:id_modelo AND cri.nombre =:nomcrite ORDER BY cri.id_criterio, sub.id_subcriterio, i.id_indicador", nativeQuery = true)
List<criteriosdesprojection> listicrinom(Long id_modelo, String nomcrite);
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM modelo WHERE fecha_inicio <=CAST(:fin AS DATE) " +
            "AND fecha_fin >=CAST(:inicio AS DATE) " +
            "AND id_modelo=(SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    Boolean existefecha(String inicio,String fin);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM modelo WHERE fecha_inicio <=CAST(:fin AS DATE) AND fecha_fin >=CAST(:inicio AS DATE) " +
            "AND id_modelo=(SELECT MAX(id_modelo) FROM modelo " +
            "WHERE id_modelo < (SELECT MAX(id_modelo) FROM modelo))", nativeQuery = true)
    Boolean fechaeditar(String inicio,String fin);
}
