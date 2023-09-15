package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Archivo_s;
import com.sistema.examenes.projection.ArchivoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Archivo_repository extends JpaRepository<Archivo_s, Long> {
    @Query(value = "SELECT * from archivo where visible =true",nativeQuery = true)
    List<Archivo_s> listararchivo();
    @Query(value = "select * from archivo ar join actividad ac on ar.id_actividad=ac.id_actividad\n" +
    "JOIN usuarios u ON ac.usuario_id = u.id where u.username=:username and ar.visible =true",nativeQuery = true)
    public List<Archivo_s> listararchivouser(String username);
    @Query(value = "SELECT * FROM archivo WHERE visible = true AND  id_actividad=:idActividad",nativeQuery = true)
    public List<Archivo_s> listararchivoActividad(Long idActividad);
    @Query(value = "SELECT u.id as idper, per.primer_nombre || ' ' || per.primer_apellido as resp, COALESCE(per.correo, 'Sin correo') AS correo,\n" +
            "ar.nombre as archiv, ac.nombre as activid, ac.fecha_inicio as ini,ac.fecha_fin as finish, ar.enlace as enlac\n" +
            "FROM archivo ar JOIN actividad ac ON ar.id_actividad=ac.id_actividad AND ar.visible=true\n" +
            "JOIN evidencia e ON e.id_evidencia = ac.id_evidencia\n" +
            "JOIN usuarios u ON u.id=ac.usuario_id\n" +
            "JOIN persona per ON per.id_persona = u.persona_id_persona\n" +
            "JOIN indicador i ON i.id_indicador = e.indicador_id_indicador\n" +
            "JOIN ponderacion po ON po.indicador_id_indicador = i.id_indicador\n" +
            "JOIN modelo mo ON mo.id_modelo = po.modelo_id_modelo\n" +
            "WHERE mo.id_modelo = (SELECT MAX(id_modelo) FROM modelo) GROUP BY idper, resp, correo, archiv, activid, ini, finish, enlac;",nativeQuery = true)
    List<ArchivoProjection> listararchi();
}
