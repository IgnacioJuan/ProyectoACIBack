package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.projection.CriteProjection;
import com.sistema.examenes.projection.CriterioSubcriteriosProjection;
import com.sistema.examenes.projection.IdCriterioProjection;
import com.sistema.examenes.projection.ValoresProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Criterio_repository extends JpaRepository<Criterio, Long> {

        @Query(value = "SELECT * from criterio where visible =true", nativeQuery = true)
        List<Criterio> listarCriterio();

        @Query(value = "SELECT c.id_criterio, c.nombre, c.descripcion, c.visible FROM indicador i JOIN subcriterio s "
                        + "ON s.id_subcriterio = i.subcriterio_id_subcriterio JOIN criterio c "
                        + "ON c.id_criterio = s.id_criterio where c.visible =true GROUP BY c.id_criterio, c.nombre ORDER BY c.id_criterio;", nativeQuery = true)
        public List<Criterio> obtenerCriterios();

        @Query(value = "SELECT c.id_criterio, c.nombre, c.descripcion, c.visible FROM asignacion_indicador ag\n"
                        + "JOIN indicador i ON ag.indicador_id_indicador = i.id_indicador\n"
                        + "JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio \n"
                        + "JOIN criterio c ON c.id_criterio = s.id_criterio \n"
                        + "WHERE ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo)\n"
                        + "GROUP BY c.id_criterio, c.nombre ORDER BY c.id_criterio;", nativeQuery = true)
        public List<Criterio> obtenerCriteriosModelo();

        @Query(value = "SELECT c.id_criterio, c.nombre, c.descripcion, c.visible FROM asignacion_indicador ag\n"
                        + "JOIN indicador i ON ag.indicador_id_indicador = i.id_indicador\n"
                        + "JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio \n"
                        + "JOIN criterio c ON c.id_criterio = s.id_criterio \n"
                        + "WHERE ag.modelo_id_modelo = :modelo\n"
                        + "GROUP BY c.id_criterio, c.nombre ORDER BY c.id_criterio;", nativeQuery = true)
        public List<Criterio> obtenerCriteriosModeloId(Long modelo);


        @Query(value = "SELECT DISTINCT c.id_criterio, c.nombre, c.descripcion, c.visible " +
                        "FROM criterio c " +
                        "JOIN subcriterio s ON c.id_criterio = s.id_criterio " +
                        "JOIN indicador i ON s.id_subcriterio = i.subcriterio_id_subcriterio " +
                        "JOIN asignacion_indicador ai ON i.id_indicador = ai.indicador_id_indicador " +
                        "WHERE ai.modelo_id_modelo = (SELECT MAX(m.id_modelo) FROM modelo m)", nativeQuery = true)
        List<Criterio> obtenerCriteriosUltimoModelo();

        @Query(value = "SELECT c.* FROM public.criterio c join public.subcriterio s ON s.id_criterio = c.id_criterio join public.indicador i ON i.subcriterio_id_subcriterio = s.id_subcriterio WHERE i.id_indicador=:id_indicador", nativeQuery = true)
        List<Criterio> listarCriterioPorIndicador(Long id_indicador);

        //
        @Query(value = "SELECT c.id_criterio, c.nombre, c.descripcion, c.visible, " +
                "(SELECT COUNT(s2.id_subcriterio) FROM subcriterio s2 WHERE s2.id_criterio = c.id_criterio AND s2.visible = true) AS cantidadSubcriterios " +
                "FROM criterio c " +
                "WHERE c.visible = true", nativeQuery = true)
        List<CriterioSubcriteriosProjection> obtenerCriteriosConCantidadSubcriterios();

        @Query(value = "SELECT cri.nombre AS \"Nomcriterio\",CAST(SUM(i.peso) AS NUMERIC(10, 2)) as \"Ponderacio\", " +
                "CAST(SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS \"VlObtenido\", " +
                "CAST(SUM(i.peso) - SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS \"Vlobtener\" " +
                "FROM indicador i JOIN subcriterio sub ON sub.id_subcriterio=i.subcriterio_id_subcriterio " +
                "JOIN criterio cri ON cri.id_criterio =sub.id_criterio " +
                "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
                "AND aa.id_modelo=?1 GROUP BY cri.nombre,cri.id_criterio  ORDER BY cri.id_criterio", nativeQuery = true)
        List<ValoresProjection> listarvalores(Long id_modelo);
        @Query(value = "SELECT cri.nombre AS \"Nomcriterio\",CAST(SUM(i.peso) AS NUMERIC(10, 2)) as \"Ponderacio\", " +
                "CAST(SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS \"VlObtenido\", " +
                "CAST(SUM(i.peso) - SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS \"Vlobtener\" " +
                "FROM indicador i JOIN subcriterio sub ON sub.id_subcriterio=i.subcriterio_id_subcriterio " +
                "JOIN criterio cri ON cri.id_criterio =sub.id_criterio " +
                "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
                "AND aa.id_modelo=?1 AND cri.nombre=?2 GROUP BY cri.nombre,cri.id_criterio  ORDER BY cri.id_criterio", nativeQuery = true)
        List<ValoresProjection> valoresporcriterio(Long id_modelo,String nombre);
        @Query(value = "SELECT cri.nombre AS \"Nomcriterio\",CAST(SUM(i.peso) AS NUMERIC(10, 2)) as \"Ponderacio\", " +
                "CAST(SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS \"VlObtenido\", " +
                "CAST(SUM(i.peso) - SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS \"Vlobtener\" " +
                "FROM indicador i JOIN subcriterio sub ON sub.id_subcriterio=i.subcriterio_id_subcriterio " +
                "JOIN criterio cri ON cri.id_criterio =sub.id_criterio " +
                "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
                "AND aa.id_modelo=?1 AND aa.usuario_id=?2 GROUP BY cri.nombre,cri.id_criterio  ORDER BY cri.id_criterio", nativeQuery = true)
        List<ValoresProjection> listarvaladmin(Long id_modelo,Long id);
        @Query(value = "SELECT cri.nombre AS Nomcriterio,CAST(SUM(i.peso) AS NUMERIC(10, 2)) as Ponderacio, " +
                "CAST(SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS VlObtenido, " +
                "CAST(SUM(i.peso) - SUM(i.porc_utilida_obtenida) AS NUMERIC(10, 2)) AS Vlobtener " +
                "FROM indicador i JOIN subcriterio sub ON sub.id_subcriterio=i.subcriterio_id_subcriterio " +
                "JOIN criterio cri ON cri.id_criterio =sub.id_criterio " +
                "JOIN asignacion_admin aa ON aa.criterio_id_criterio=cri.id_criterio AND aa.visible=true " +
                "AND aa.id_modelo=:id_modelo AND aa.criterio_id_criterio IN " +
                "(SELECT DISTINCT cri.id_criterio FROM asignacion_evidencia ae " +
                "JOIN evidencia e ON ae.evidencia_id_evidencia = e.id_evidencia AND ae.visible=true " +
                "JOIN indicador i ON i.id_indicador = e.indicador_id_indicador " +
                "JOIN subcriterio s ON s.id_subcriterio = i.subcriterio_id_subcriterio " +
                "JOIN criterio cri ON cri.id_criterio = s.id_criterio " +
                "WHERE ae.usuario_id =:id AND ae.id_modelo =:id_modelo ) GROUP BY cri.nombre,cri.id_criterio  ORDER BY cri.id_criterio", nativeQuery = true)
        List<ValoresProjection> listarvalresp(Long id_modelo,Long id);
        @Query(value = "SELECT id_criterio FROM criterio WHERE nombre=:nombre", nativeQuery = true)
        public IdCriterioProjection idcriterio(String nombre);

        @Query(value = "SELECT CASE WHEN criterio.nombre IS NOT NULL THEN criterio.nombre ELSE '' END AS criterio, " +
                "CASE WHEN evidencia.descripcion IS NOT NULL THEN evidencia.descripcion ELSE '' END AS evidencia " +
                "FROM usuarios u " +
                "LEFT JOIN usuariorol ur ON u.id = ur.usuario_id " +
                "LEFT JOIN asignacion_admin aa ON aa.usuario_id = u.id AND aa.visible = true AND aa.id_modelo =:id_modelo " +
                "LEFT JOIN criterio criterio ON aa.criterio_id_criterio = criterio.id_criterio " +
                "LEFT JOIN asignacion_evidencia ae ON ae.usuario_id = u.id AND ae.visible = true AND ae.id_modelo =:id_modelo " +
                "LEFT JOIN evidencia evidencia ON ae.evidencia_id_evidencia = evidencia.id_evidencia " +
                "WHERE u.id =:id " +
                "ORDER BY criterio.id_criterio, evidencia.id_evidencia", nativeQuery = true)
         List<CriteProjection> actividadesusuario(Long id, Long id_modelo);

}
