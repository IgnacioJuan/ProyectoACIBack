package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Criterio;
import com.sistema.examenes.entity.Observacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Observacion_repository extends JpaRepository<Observacion, Long> {

    @Query(value = "SELECT * from observacion where visible =true",nativeQuery = true)
    List<Observacion> listarObservacion();
    @Query(value = "SELECT * FROM observacion WHERE actividad_id_actividad =:id_actividad AND visible = true " +
            "AND (observacion != 'Ninguna' OR id_observacion = (SELECT MAX(id_observacion) " +
            "FROM observacion WHERE observacion = 'Ninguna' AND actividad_id_actividad =:id_actividad " +
            "AND visible = true))",nativeQuery = true)
    List<Observacion> observacionactividad(Long id_actividad);
    @Query(value = "SELECT * from observacion o JOIN usuarios u ON u.id=o.usuario_id " +
            "JOIN actividad ac ON ac.id_actividad=o.actividad_id_actividad WHERE o.visible =true and u.username=:user " +
            "and ac.id_actividad=:id",nativeQuery = true)
    List<Observacion> observacionUsuario(String user, Long id);

    @Modifying
    @Query(value = "DELETE FROM observacion WHERE id_observacion=:id", nativeQuery = true)
    void borrar(Long id);
    
    @Query(value = "SELECT * FROM observacion WHERE actividad_id_actividad=:idact AND visible=true",nativeQuery = true)
    List<Observacion> observacionActividad(Long idact);
    
}
