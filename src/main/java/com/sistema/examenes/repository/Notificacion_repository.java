package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface Notificacion_repository extends JpaRepository<Notificacion, Long> {
    @Query(value = "SELECT * FROM notificacion WHERE usuario=:user ORDER BY fecha DESC;",nativeQuery = true)
    List<Notificacion> listarUserNoti(Long user);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM notificacion WHERE id=:id", nativeQuery = true)
    void borrar(Long id);

    @Query(value = "SELECT * FROM notificacion WHERE DATE(fecha)<CAST(:fec AS DATE)", nativeQuery = true)
    List<Notificacion> listarNot(String fec);
    @Query(value = "SELECT * FROM notificacion WHERE rol=:roluser ORDER BY fecha DESC LIMIT(20)", nativeQuery = true)
    List<Notificacion> all(String roluser);
    @Query(value = "SELECT * FROM notificacion WHERE rol=:roluser ORDER BY fecha ", nativeQuery = true)
    List<Notificacion> all2(String roluser);

    @Query(value = "SELECT DISTINCT ON (mensaje)* FROM notificacion WHERE usuario=:user ORDER BY mensaje, fecha DESC;",nativeQuery = true)
    List<Notificacion> listarulNoti(Long user);
    @Query(value = "SELECT DATE(fecha_fin) FROM modelo WHERE id_modelo=(SELECT MAX(id_modelo) FROM modelo)", nativeQuery = true)
    Date fechaeliminar();
}
