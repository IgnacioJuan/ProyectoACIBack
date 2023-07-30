package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Indicador;
import com.sistema.examenes.entity.Subcriterio;
import com.sistema.examenes.projection.CriterioSubcriteriosProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Subcriterio_repository extends JpaRepository<Subcriterio, Long> {
    @Query(value = "SELECT * from subcriterio where visible =true", nativeQuery = true)
    List<Subcriterio> listarSubcriterio();

    // un query para buscar por id_criterio
    @Query(value = "SELECT * from subcriterio where id_criterio = :id_criterio and visible =true", nativeQuery = true)
    List<Subcriterio> listarSubcriterioPorCriterio(Long id_criterio);

    @Query(value = "SELECT s.id_subcriterio, s.nombre, s.descripcion, s.visible, " +
            "(SELECT COUNT(i2.id_indicador) " +
            "FROM indicador i2 WHERE i2.subcriterio_id_subcriterio = s.id_subcriterio AND i2.visible = true) " +
            "AS cantidadIndicadores " +
            "FROM subcriterio s " +
            "LEFT JOIN indicador i " +
            "ON s.id_subcriterio = i.subcriterio_id_subcriterio "+
            "where s.visible =true and s.id_criterio=:id_criterio "+
            "GROUP BY s.id_subcriterio", nativeQuery = true)
    List<SubcriterioIndicadoresProjection> obtenerSubcirteriosConCantidadIndicador(Long id_criterio);
}
