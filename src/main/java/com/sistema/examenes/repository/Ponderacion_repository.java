package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Ponderacion;
import com.sistema.examenes.projection.PonderacionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface Ponderacion_repository extends JpaRepository<Ponderacion, Long> {
    @Query(value = "SELECT * from ponderacion where visible =true", nativeQuery = true)
    List<Ponderacion> listarPonderacion();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ponderacion WHERE contador =?1 AND fecha=TO_DATE(?2, 'YYYY-MM-DD')", nativeQuery = true)
    void eliminarPonderacion(Long contador, String fecha);
    @Query(value = "SELECT p.contador, p.fecha as fechapo FROM ponderacion p JOIN modelo m ON m.id_modelo = p.modelo_id_modelo WHERE m.id_modelo =:id_modelo GROUP BY p.contador, p.fecha ORDER BY p.contador", nativeQuery = true)
    List<Ponderacion> listarPonderacionPorModelo(Long id_modelo);
    @Query(value = "SELECT p.contador, p.fecha as fechapo FROM ponderacion p JOIN modelo m ON m.id_modelo = p.modelo_id_modelo WHERE m.id_modelo =:id_modelo GROUP BY p.contador, p.fecha ORDER BY p.contador", nativeQuery = true)
    List<PonderacionProjection> listarPonderacionModelo(Long id_modelo);

    @Query(value = "SELECT p.* FROM ponderacion p JOIN modelo m ON m.id_modelo = p.modelo_id_modelo WHERE p.fecha = TO_DATE(:fecha, 'YYYY-MM-DD')", nativeQuery = true)
    List<Ponderacion> listarPorFecha(@Param("fecha") String fecha);


    @Query(value = "SELECT p.* FROM ponderacion p JOIN modelo m ON m.id_modelo = p.modelo_id_modelo WHERE p.fecha = TO_DATE(?1, 'YYYY-MM-DD') AND p.contador = ?2", nativeQuery = true)
    List<Ponderacion> listarPonderacionPorFecha(String fecha, Long contador);

    @Query(value = "SELECT p.* FROM ponderacion p JOIN modelo m ON p.modelo_id_modelo = m.id_modelo WHERE m.id_modelo = :id_modelo", nativeQuery = true)
    List<Ponderacion> listarPondeModelo(@Param("id_modelo") Long id_modelo);
    @Query(value = "SELECT contador FROM ponderacion WHERE modelo_id_modelo = :id_modelo ORDER BY contador DESC LIMIT 1", nativeQuery = true)
    List<PonderacionProjection> idmax(Long id_modelo);
}
