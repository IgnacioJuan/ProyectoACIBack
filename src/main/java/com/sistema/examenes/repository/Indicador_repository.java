package com.sistema.examenes.repository;

import java.util.List;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sistema.examenes.entity.Indicador;

public interface Indicador_repository extends JpaRepository<Indicador, Long> {
    // un query para buscar por id_subcriterio
    @Query(value = "SELECT * from indicador where subcriterio_id_subcriterio = :id_subcriterio", nativeQuery = true)
    List<Indicador> listarIndicadorPorSubcriterio(Long id_subcriterio);

    @Query(value = "SELECT i.* from indicador i JOIN subcriterio s ON s.id_subcriterio=i.subcriterio_id_subcriterio\n" +
            "JOIN criterio cri ON cri.id_criterio = s.id_criterio " +
            "where i.visible =true ORDER BY  cri.id_criterio, s.id_subcriterio, i.id_indicador", nativeQuery = true)
    List<Indicador> listarIndicador();

    @Query(value = "SELECT *  FROM indicador i JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio JOIN criterio c ON c.id_criterio = s.id_criterio "
            +
            "WHERE c.id_criterio = :criterio GROUP BY i.id_indicador,s.id_subcriterio, c.id_criterio, c.nombre ORDER BY i.id_indicador", nativeQuery = true)
    public List<Indicador> obtenerIndicadores(Long criterio);

    @Query(value = "SELECT cri.nombre AS nombre, " +
            "CAST(SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 4)) AS total, " +
            "CAST(SUM(i.peso) AS NUMERIC(10, 4)) AS faltante " +
            "FROM indicador i JOIN subcriterio sub ON sub.id_subcriterio=i.subcriterio_id_subcriterio " +
            "JOIN criterio cri ON cri.id_criterio =sub.id_criterio " +
            "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
            "AND aa.id_modelo=:id_modelo GROUP BY cri.nombre,cri.id_criterio  ORDER BY cri.id_criterio", nativeQuery = true)
    public List<IndicadoresProjection> Indicadores(Long id_modelo);

    @Query(value = "SELECT cri.nombre AS nombre, " +
            "CAST(SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 4)) AS total, " +
            "CAST(SUM(i.peso) AS NUMERIC(10, 4)) AS faltante " +
            "FROM indicador i JOIN subcriterio sub ON sub.id_subcriterio=i.subcriterio_id_subcriterio " +
            "JOIN criterio cri ON cri.id_criterio =sub.id_criterio " +
            "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
            "AND aa.id_modelo=:id_modelo AND aa.usuario_id=:id GROUP BY cri.nombre,cri.id_criterio  ORDER BY cri.id_criterio", nativeQuery = true)
    public List<IndicadoresProjection> indicadoresadmin(Long id_modelo,Long id);
    @Query(value = "SELECT i.* FROM public.modelo m join public.asignacion_indicador a ON " +
            "a.modelo_id_modelo = m.id_modelo JOIN public.indicador i on a.indicador_id_indicador = i.id_indicador AND a.visible=true " +
            "JOIN public.subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio JOIN public.criterio c " +
            "ON c.id_criterio = s.id_criterio WHERE c.id_criterio= :id_criterio and m.id_modelo= :id_modelo " +
            "ORDER BY i.id_indicador", nativeQuery = true)
    List<Indicador> listarIndicadorPorCriterioModelo(Long id_criterio, Long id_modelo);

    @Query("SELECT DISTINCT i FROM Indicador i " +
            "JOIN i.subcriterio s " +
            "JOIN s.criterio c " +
            "JOIN Asignacion_Indicador ai ON ai.indicador = i " +
            "WHERE ai.modelo.id_modelo = (SELECT MAX(m.id_modelo) FROM Modelo m) " +
            "AND (c.id_criterio IN :idCriterios OR COALESCE(:idCriterios, NULL) IS NULL)")
    List<Indicador> indicadoresPorCriterios(List<Long> idCriterios);

    @Query("SELECT DISTINCT i FROM Indicador i " +
            "JOIN i.subcriterio s " +
            "JOIN s.criterio c " +
            "JOIN Asignacion_Indicador ai ON ai.indicador = i " +
            "WHERE ai.modelo.id_modelo = (SELECT MAX(m.id_modelo) FROM Modelo m) " +
            "AND (c.id_criterio IN :idCriterios OR COALESCE(:idCriterios, NULL) IS NULL) AND i.tipo='cualitativa'")
    List<Indicador> indicadoresPorCriteriosPruebaCuali(List<Long> idCriterios);

    @Query("SELECT DISTINCT i FROM Indicador i " +
            "JOIN i.subcriterio s " +
            "JOIN s.criterio c " +
            "JOIN Asignacion_Indicador ai ON ai.indicador = i " +
            "WHERE ai.modelo.id_modelo = (SELECT MAX(m.id_modelo) FROM Modelo m) " +
            "AND (c.id_criterio IN :idCriterios OR COALESCE(:idCriterios, NULL) IS NULL) AND i.tipo='cuantitativa'")
    List<Indicador> indicadoresPorCriteriosPruebaCuanti(List<Long> idCriterios);

    @Query(value ="SELECT DISTINCT m.id_modelo, c.nombre, s.nombre, \n" +
            "i.nombre, e.nombre, i.valor_obtenido, i.porc_obtenido, i.porc_utilida_obtenida, \n" +
            "de.observacion \n" +
            "FROM criterio c \n" +
            "LEFT JOIN subcriterio s ON c.id_criterio= s.id_criterio \n" +
            "LEFT JOIN indicador i ON s.id_subcriterio = i.subcriterio_id_subcriterio \n" +
            "LEFT JOIN asignacion_indicador ai ON i.id_indicador = ai.indicador_id_indicador \n" +
            "LEFT JOIN modelo m ON ai.modelo_id_modelo = m.id_modelo \n" +
            "LEFT JOIN evidencia e ON i.id_indicador = e.indicador_id_indicador \n" +
            "LEFT JOIN detalle_evaluacion de ON e.id_evidencia = de.evidencia_id_evidencia \n" +
            "WHERE ai.modelo_id_modelo = (SELECT MAX(m.id_modelo) FROM Modelo m) AND i.tipo='cualitativa' \n" +
            "ORDER BY m.id_modelo ", nativeQuery = true)
    List<Indicador> indicadoresPorCriteriosCuali();

    @Query(value ="SELECT DISTINCT m.id_modelo, c.nombre, s.nombre, \n" +
            "i.nombre, e.nombre, i.valor_obtenido, i.porc_obtenido, i.porc_utilida_obtenida, \n" +
            "de.observacion \n" +
            "FROM criterio c \n" +
            "LEFT JOIN subcriterio s ON c.id_criterio= s.id_criterio \n" +
            "LEFT JOIN indicador i ON s.id_subcriterio = i.subcriterio_id_subcriterio \n" +
            "LEFT JOIN asignacion_indicador ai ON i.id_indicador = ai.indicador_id_indicador \n" +
            "LEFT JOIN modelo m ON ai.modelo_id_modelo = m.id_modelo \n" +
            "LEFT JOIN evidencia e ON i.id_indicador = e.indicador_id_indicador \n" +
            "LEFT JOIN detalle_evaluacion de ON e.id_evidencia = de.evidencia_id_evidencia \n" +
            "WHERE ai.modelo_id_modelo = (SELECT MAX(m.id_modelo) FROM Modelo m) AND i.tipo='cuantitativa' \n" +
            "ORDER BY m.id_modelo ", nativeQuery = true)
    List<Indicador> indicadoresPorCriteriosCuanti();

    @Query(value = "SELECT i.id_indicador,i.nombre, i.descripcion, i.peso, i.estandar, i.tipo ," +
            "i.valor_obtenido,i.porc_obtenido,i.porc_utilida_obtenida, i.visible, " +
            "(SELECT COUNT(e2.id_evidencia) " +
            "FROM evidencia e2 WHERE e2.indicador_id_indicador = i.id_indicador AND e2.visible = true) " +
            "AS cantidadEvidencia " +
            "FROM indicador i " +
            "LEFT JOIN evidencia e " +
            "ON i.id_indicador = e.indicador_id_indicador "+
            "WHERE i.visible = true AND i.subcriterio_id_subcriterio = :id_subcriterio " +
            "GROUP BY i.id_indicador", nativeQuery = true)
    List<IndicadorEvidenciasProjection> obtenerIndicadoresConCantidadEvidencia(Long id_subcriterio);
    @Query(value = "SELECT i.id_indicador, i.nombre indicador, c.nombre criterio, s.nombre subcriterio, e.nombre evidencia, i.descripcion, i.peso, i.estandar, i.tipo ," +
            "i.valor_obtenido,i.porc_obtenido,i.porc_utilida_obtenida, i.visible, " +
            "(SELECT COUNT(e2.id_evidencia) " +
            "FROM evidencia e2 WHERE e2.indicador_id_indicador = i.id_indicador AND e2.visible = true) " +
            "AS cantidadEvidencia," +
            "s.nombre as nombreSubcriterio " +
            "FROM indicador i " +
            "JOIN subcriterio s " +
            "ON s.id_subcriterio = i.subcriterio_id_subcriterio " +
            "JOIN criterio c " +
            "ON c.id_criterio = s.id_criterio " +
            "LEFT JOIN evidencia e " +
            "ON i.id_indicador = e.indicador_id_indicador "+
            "WHERE i.visible = true " +
            "GROUP BY i.id_indicador, c.nombre, s.nombre, e.nombre " +
            "ORDER BY s.nombre, i.id_indicador ", nativeQuery = true)
    List<IndicadorEvidenciasProjectionFull> obtenerIndicadoresConCantidadEvidenciaFull();



    @Query("SELECT DISTINCT i FROM Indicador i " +
            "JOIN i.subcriterio s " +
            "JOIN Asignacion_Indicador ai ON ai.indicador = i " +
            "WHERE ai.modelo.id_modelo = :id_modelo " +
            "AND i.visible = true")
    List<Indicador> indicadoresPorModelo(Long id_modelo);

    /*@Query(value = "SELECT COUNT(ai.indicador_id_indicador) AS indica, " +
            "CASE WHEN i.porc_obtenido > 75 THEN 'verde' " +
            "WHEN i.porc_obtenido > 50 AND i.porc_obtenido <= 75 THEN 'amarillo' " +
            "WHEN i.porc_obtenido > 25 AND i.porc_obtenido <= 50 THEN 'naranja' " +
            "ELSE 'rojo'END as color, " +
            "ROUND(COUNT(ai.indicador_id_indicador) * 100.0 / (SELECT COUNT(indicador_id_indicador) FROM asignacion_indicador " +
            "WHERE modelo_id_modelo=:id_modelo AND visible =true), 2) AS porcentaje " +
            "FROM asignacion_indicador ai JOIN indicador i ON i.id_indicador=ai.indicador_id_indicador AND ai.visible=true " +
            "JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio AND i.visible=true " +
            "JOIN criterio cri ON cri.id_criterio = s.id_criterio " +
            "JOIN modelo mo ON ai.modelo_id_modelo=mo.id_modelo " +
            "WHERE mo.id_modelo=:id_modelo AND i.visible=true GROUP BY color", nativeQuery = true)
    List<IndiColProjection> indicadorval(Long id_modelo);*/
    @Query(value = "WITH color_values AS ( " +
            "SELECT 'verde' AS color UNION ALL SELECT 'amarillo' " +
            "UNION ALL SELECT 'naranja' UNION ALL SELECT 'rojo') " +
            "SELECT COALESCE(counts.indica, 0) AS indica, cv.color AS color, " +
            "ROUND(COALESCE(counts.indica, 0) * 100.0 / NULLIF(total.total_count, 0), 2) AS porcentaje " +
            "FROM color_values cv LEFT JOIN (SELECT CASE " +
            "WHEN i.porc_obtenido > 75 THEN 'verde' " +
            "WHEN i.porc_obtenido > 50 AND i.porc_obtenido <= 75 THEN 'amarillo' " +
            "WHEN i.porc_obtenido > 25 AND i.porc_obtenido <= 50 THEN 'naranja' " +
            "ELSE 'rojo' END AS color, " +
            "COUNT(ai.indicador_id_indicador) AS indica FROM asignacion_indicador ai " +
            "JOIN indicador i ON i.id_indicador = ai.indicador_id_indicador AND ai.visible = true " +
            "JOIN modelo mo ON ai.modelo_id_modelo = mo.id_modelo " +
            "WHERE mo.id_modelo =:id_modelo AND i.visible = true GROUP BY color " +
            ") counts ON cv.color = counts.color LEFT JOIN ( " +
            "SELECT COUNT(indicador_id_indicador) AS total_count " +
            "FROM asignacion_indicador WHERE modelo_id_modelo =:id_modelo AND visible = true) " +
            "AS total ON 1=1;", nativeQuery = true)
    List<IndiColProjection> indicadorval(Long id_modelo);
}
