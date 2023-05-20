package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Criterio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Actividad_repository extends JpaRepository<Actividad, Long> {

    @Query(value = "SELECT * from actividad where visible =true", nativeQuery = true)
    List<Actividad> listarActividad();


    @Query(value = "SELECT ac.id_actividad, ac.descripcion, ac.fecha_fin, ac.fecha_inicio, ac.nombre, ac.visible, ac.id_evidencia, ac.usuario_id\n"
            + "FROM actividad ac \n"
            + "JOIN evidencia e ON ac.id_evidencia = e.id_evidencia\n"
            + "JOIN indicador i ON e.indicador_id_indicador = i.id_indicador\n"
            + "JOIN asignacion_indicador ag ON ag.indicador_id_indicador = i.id_indicador\n"
            + "LEFT JOIN ponderacion po ON i.id_indicador = po.indicador_id_indicador\n"
            + "WHERE po.indicador_id_indicador IS NULL\n"
            + "AND ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo)\n"
            + "AND ac.fecha_fin < CURRENT_DATE;", nativeQuery = true)
    List<Actividad> listarActividadAtrasadas();

    @Query(value = "SELECT ac.id_actividad, ac.descripcion, ac.fecha_fin, ac.fecha_inicio, ac.nombre, ac.visible, ac.evidencia_id_evidencia, ac.usuario_id\n" +
"FROM actividad ac \n" +
"JOIN evidencia e ON ac.evidencia_id_evidencia = e.id_evidencia\n" +
"JOIN indicador i ON e.indicador_id_indicador = i.id_indicador\n" +
"JOIN asignacion_indicador ag ON ag.indicador_id_indicador = i.id_indicador\n" +
"JOIN ponderacion po ON i.id_indicador = po.indicador_id_indicador\n" +
"WHERE ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo);", nativeQuery = true)
    List<Actividad> listarActividadCumplidas();
    
    @Query(value = "SELECT ac.id_actividad, ac.descripcion, ac.fecha_fin, ac.fecha_inicio, ac.nombre, ac.visible, ac.id_evidencia, ac.usuario_id\n" +
"FROM modelo m, actividad ac \n" +
"JOIN evidencia e ON ac.id_evidencia = e.id_evidencia\n" +
"JOIN indicador i ON e.indicador_id_indicador = i.id_indicador\n" +
"JOIN asignacion_indicador ag ON ag.indicador_id_indicador = i.id_indicador\n" +
"LEFT JOIN detalle_evaluacion d ON d.evidencia_id_evidencia = e.id_evidencia\n" +
"WHERE ag.modelo_id_modelo = (SELECT MAX(id_modelo) FROM modelo)\n" +
"AND d.estado IS NULL OR d.estado = 'false'\n" +
"AND CURRENT_DATE < (SELECT fecha_fin FROM modelo WHERE id_modelo = (SELECT MAX(id_modelo) FROM modelo));", nativeQuery = true)
    List<Actividad> listarEvideRechazadasFecha();
    

    @Query(value = "select * from  actividad ac JOIN usuarios u ON ac.usuario_id = u.id where u.username=:username and ac.visible =true",nativeQuery = true)
    List<Actividad>listarporusuario(String username);
    List<Actividad> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = "SELECT * FROM actividad WHERE visible= true AND id_evidencia=:idEvidendicia ;",nativeQuery = true)
    List<Actividad>listarporEvidencia(Long idEvidendicia);


}
