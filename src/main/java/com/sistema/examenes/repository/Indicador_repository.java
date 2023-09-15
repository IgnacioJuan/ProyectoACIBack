package com.sistema.examenes.repository;

import java.util.List;

import com.sistema.examenes.projection.IndicadorEvidenciasProjection;
import com.sistema.examenes.projection.IndicadorEvidenciasProjectionFull;
import com.sistema.examenes.projection.IndicadoresProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sistema.examenes.entity.Indicador;

public interface Indicador_repository extends JpaRepository<Indicador, Long> {
    // un query para buscar por id_subcriterio
    @Query(value = "SELECT * from indicador where subcriterio_id_subcriterio = :id_subcriterio", nativeQuery = true)
    List<Indicador> listarIndicadorPorSubcriterio(Long id_subcriterio);

    @Query(value = "SELECT * from indicador where visible =true", nativeQuery = true)
    List<Indicador> listarIndicador();

    @Query(value = "SELECT *  FROM indicador i JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio JOIN criterio c ON c.id_criterio = s.id_criterio "
            +
            "WHERE c.id_criterio = :criterio GROUP BY i.id_indicador,s.id_subcriterio, c.id_criterio, c.nombre ORDER BY i.id_indicador", nativeQuery = true)
    public List<Indicador> obtenerIndicadores(Long criterio);

    @Query(value = "SELECT cri.nombre as nombre, SUM(i.porc_utilida_obtenida) as total\n" +
            "FROM indicador i JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio\n" +
            "JOIN criterio cri ON cri.id_criterio = s.id_criterio JOIN ponderacion po ON po.indicador_id_indicador=i.id_indicador\n" +
            "JOIN modelo mo ON po.modelo_id_modelo=mo.id_modelo\n" +
            "WHERE mo.id_modelo=(SELECT MAX(id_modelo) FROM modelo) AND i.visible=true GROUP BY cri.nombre", nativeQuery = true)
    public List<IndicadoresProjection> Indicadores();


    @Query(value = "SELECT i.* FROM public.modelo m join public.asignacion_indicador a ON a.modelo_id_modelo = m.id_modelo JOIN public.indicador i on a.indicador_id_indicador = i.id_indicador JOIN public.subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio JOIN public.criterio c ON c.id_criterio = s.id_criterio WHERE c.id_criterio= :id_criterio and m.id_modelo= :id_modelo ORDER BY i.nombre", nativeQuery = true)
    List<Indicador> listarIndicadorPorCriterioModelo(Long id_criterio, Long id_modelo);

    @Query("SELECT DISTINCT i FROM Indicador i " +
            "JOIN i.subcriterio s " +
            "JOIN s.criterio c " +
            "JOIN Asignacion_Indicador ai ON ai.indicador = i " +
            "WHERE ai.modelo.id_modelo = (SELECT MAX(m.id_modelo) FROM Modelo m) " +
            "AND (c.id_criterio IN :idCriterios OR COALESCE(:idCriterios, NULL) IS NULL)")
    List<Indicador> indicadoresPorCriterios(List<Long> idCriterios);

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
    @Query(value = "SELECT i.id_indicador,i.nombre, i.descripcion, i.peso, i.estandar, i.tipo ," +
            "i.valor_obtenido,i.porc_obtenido,i.porc_utilida_obtenida, i.visible, " +
            "(SELECT COUNT(e2.id_evidencia) " +
            "FROM evidencia e2 WHERE e2.indicador_id_indicador = i.id_indicador AND e2.visible = true) " +
            "AS cantidadEvidencia," +
            "s.nombre as nombreSubcriterio " +
            "FROM indicador i " +
            "JOIN subcriterio s " +
            "ON s.id_subcriterio = i.subcriterio_id_subcriterio " +
            "LEFT JOIN evidencia e " +
            "ON i.id_indicador = e.indicador_id_indicador "+
            "WHERE i.visible = true " +
            "GROUP BY i.id_indicador, s.nombre " +
            "ORDER BY s.nombre, i.id_indicador ", nativeQuery = true)
    List<IndicadorEvidenciasProjectionFull> obtenerIndicadoresConCantidadEvidenciaFull();

    @Query("SELECT DISTINCT i FROM Indicador i " +
            "JOIN i.subcriterio s " +
            "JOIN Asignacion_Indicador ai ON ai.indicador = i " +
            "WHERE ai.modelo.id_modelo = :id_modelo " +
            "AND i.visible = true")
    List<Indicador> indicadoresPorModelo(Long id_modelo);
}
