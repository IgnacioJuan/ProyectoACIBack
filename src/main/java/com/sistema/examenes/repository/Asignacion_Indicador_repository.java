package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Asignacion_Indicador;
import com.sistema.examenes.entity.Modelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface Asignacion_Indicador_repository extends JpaRepository<Asignacion_Indicador, Long> {

    @Query(value = "SELECT * from asignacion_indicador where visible =true", nativeQuery = true)
    List<Asignacion_Indicador> listarAsignacionIndicador();

    // metodo para lista asignacion_indicador por objeto modelo
    List<Asignacion_Indicador> findByModelo(Modelo modelo);

    @Query(value = "SELECT * FROM asignacion_indicador WHERE modelo_id_modelo=:id_modelo", nativeQuery = true)
    List<Asignacion_Indicador> listarAsignacion(Long id_modelo);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM asignacion_indicador WHERE modelo_id_modelo=:id AND indicador_id_indicador=:id_asig", nativeQuery = true)
    void eliminarasignacion(Long id,Long id_asig);
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM asignacion_indicador WHERE indicador_id_indicador=:id_indi AND modelo_id_modelo=:id_modelo AND visible=true;", nativeQuery = true)
    Boolean existeIndicador(Long id_indi,Long id_modelo);
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM asignacion_admin aa " +
            "JOIN criterio cri ON cri.id_criterio=aa.criterio_id_criterio AND aa.visible=true " +
            "JOIN subcriterio s ON s.id_criterio=cri.id_criterio " +
            "JOIN indicador i ON s.id_subcriterio= i.subcriterio_id_subcriterio " +
            "JOIN asignacion_indicador ai ON ai.indicador_id_indicador=i.id_indicador " +
            "WHERE aa.id_modelo=:id_modelo AND ai.indicador_id_indicador=:id_indi ;", nativeQuery = true)
    Boolean existeCriterio(Long id_modelo, Long id_indi);
    @Query(value = "SELECT cri.nombre FROM criterio cri " +
            "JOIN subcriterio s ON s.id_criterio = cri.id_criterio " +
            "JOIN indicador i ON i.subcriterio_id_subcriterio=s.id_subcriterio " +
            "JOIN asignacion_indicador ai ON ai.indicador_id_indicador=i.id_indicador " +
            "WHERE ai.indicador_id_indicador=:id_asig", nativeQuery = true)
    String nombreCriterio(@Param("id_asig") Long id_asig);

    @Query(value = "SELECT COUNT(ai.indicador_id_indicador) FROM asignacion_indicador ai " +
            "JOIN indicador i ON i.id_indicador=ai.indicador_id_indicador " +
            "JOIN subcriterio s ON s.id_subcriterio=i.subcriterio_id_subcriterio " +
            "JOIN criterio cri ON cri.id_criterio=s.id_criterio " +
            "WHERE ai.modelo_id_modelo=:id_modelo AND cri.nombre=:nombre", nativeQuery = true)
    Integer contar(Long id_modelo, String nombre);
}
