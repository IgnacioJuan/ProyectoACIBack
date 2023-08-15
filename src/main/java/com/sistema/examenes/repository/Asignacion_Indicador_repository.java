package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Asignacion_Indicador;
import com.sistema.examenes.entity.Modelo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Asignacion_Indicador_repository extends JpaRepository<Asignacion_Indicador, Long> {

    @Query(value = "SELECT * from asignacion_indicador where visible =true", nativeQuery = true)
    List<Asignacion_Indicador> listarAsignacionIndicador();

    // metodo para lista asignacion_indicador por objeto modelo
    List<Asignacion_Indicador> findByModelo(Modelo modelo);

    @Query(value = "SELECT indicador_id_indicador FROM asignacion_indicador WHERE modelo_id_modelo=:id_modelo", nativeQuery = true)
    List<Asignacion_Indicador> listarAsignacion(Long id_modelo);
}
