package com.sistema.examenes.repository;


import com.sistema.examenes.entity.Persona;
import com.sistema.examenes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Persona_repository extends JpaRepository<Persona, Long> {
    @Query(value = "SELECT * FROM persona p JOIN usuarios u ON p.id_persona = u.persona_id_persona WHERE u.username = :username", nativeQuery = true)
    public Persona obtenerPersona(String username);
    
    @Query(value = "SELECT * FROM persona p JOIN usuarios u ON p.id_persona = u.persona_id_persona WHERE u.id = :id", nativeQuery = true)
    public Persona obtenerPersonaUsuario(Long id);

    public Persona findByCedula(String cedula);
}
