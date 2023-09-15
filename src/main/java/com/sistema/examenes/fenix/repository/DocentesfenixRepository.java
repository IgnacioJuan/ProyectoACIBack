package com.sistema.examenes.fenix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sistema.examenes.fenix.entity.Docentesfenix;

public interface DocentesfenixRepository extends JpaRepository<Docentesfenix, String> {
    @Query("SELECT d FROM Docentesfenix d WHERE UPPER(d.primer_nombre) = UPPER(:primer_nombre)")
    List<Docentesfenix> findByPrimer_Nombre(@Param("primer_nombre") String primer_nombre);
    @Query("SELECT d FROM Docentesfenix d WHERE UPPER(d.primer_nombre) = UPPER(:primer_nombre) AND UPPER(d.primer_apellido) = UPPER(:primer_apellido)")
    List<Docentesfenix> findBynombresCompletos(@Param("primer_nombre") String primer_nombre, @Param("primer_apellido") String primer_apellido);
    @Query("SELECT d FROM Docentesfenix d WHERE UPPER(d.primer_apellido) = UPPER(:primer_apellido)")
    List<Docentesfenix> findByPrimer_Apellido(@Param("primer_apellido") String primer_apellido);

    @Query("SELECT d FROM Docentesfenix d WHERE UPPER(d.segundo_apellido) = UPPER(:segundo_apellido)")
    List<Docentesfenix> findBySegundo_Apellido(@Param("segundo_apellido") String segundo_apellido);

    // metodo para buscar un docente por su segundo apellido
    @Query("SELECT d FROM Docentesfenix d WHERE UPPER(d.segundo_apellido) = UPPER(:segundo_apellido) OR UPPER(d.primer_apellido) = UPPER(:primer_apellido)")
    List<Docentesfenix> findByPrimer_ApellidoAndSegundo_Apellido(@Param("primer_apellido") String primer_apellido,
            @Param("segundo_apellido") String segundo_apellido);

    // metodo para listar todos los docentes
    List<Docentesfenix> findAll();

    // metodo para buscar un docente por su id
    List<Docentesfenix> findByCedula(String cedula);

    // // metodo para buscar un docente por su primer apellido
    // List<Docentesfenix> findByPrimer_Apellido(String primer_apellido);

    // // metodo para buscar un docente por su segundo apellido
    // List<Docentesfenix> findBySegundoApellido(String segundo_apellido);
}
