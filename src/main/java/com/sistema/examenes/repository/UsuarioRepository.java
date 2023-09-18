package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Usuario;
import java.util.List;

import com.sistema.examenes.projection.ResponsableProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        @Query(value = "SELECT *\n" +
                        "\tFROM usuarios WHERE username = :username AND visible=true", nativeQuery = true)
        public Usuario findByUsername(String username);

        @Query(value = "SELECT * FROM usuarios WHERE username = :username", nativeQuery = true)
        public Usuario findAllByUsername(String username);

        @Query(value = "SELECT *\n" +
                        "\tFROM usuarios WHERE enabled = true AND visible=true", nativeQuery = true)
        public abstract List<Usuario> listar();

        @Query(value = "SELECT * FROM usuarios;", nativeQuery = true)
        public abstract List<Usuario> listaResponsables();

        @Query(value = "SELECT * FROM usuarios WHERE username=:user", nativeQuery = true)
        public Usuario buscarId(String user);

        /*
         * @Query(value = "SELECT u.* " +
         * "FROM usuarios u " +
         * "JOIN usuariorol ur ON u.id = ur.usuario_id " +
         * "LEFT JOIN asignacion_evidencia ae ON u.id = ae.usuario_id " +
         * "WHERE ur.rol_rolid = 3 AND ae.id_asignacion_evidencia IS NULL AND u.visible=true"
         * , nativeQuery = true)
         */
        // @Query(value = "SELECT u.*\n" +
        // " FROM usuarios u \n" +
        // " JOIN usuariorol ur ON u.id = ur.usuario_id \n" +
        // " WHERE ur.rol_rolid = 3 AND u.visible=true", nativeQuery = true)
        // public List<Usuario> listaResponsablesAdmin();

        @Query(value = "SELECT * FROM usuarios u\n" +
                        "JOIN asignacion_evidencia ae ON u.id = ae.usuario_id\n" +
                        "JOIN persona p  ON u.persona_id_persona = p.id_persona\n" +
                        "WHERE u.visible = true AND ae.visible = true;", nativeQuery = true)
        public List<Usuario> listaResponsablesDatos();

        @Query(value = "SELECT u.* FROM usuarios u " +
                        "JOIN usuariorol ur ON u.id = ur.usuario_id " +
                        "LEFT JOIN asignacion_evidencia ae ON u.id = ae.usuario_id " +
                        "WHERE ur.rol_rolid = 3 AND ae.id_asignacion_evidencia IS NULL AND u.visible=true", nativeQuery = true)
        public List<Usuario> listaResponsablesAdmin();
        /*@Query(value = "SELECT u.id, per.primer_nombre || ' ' || per.primer_apellido as nombres, u.username as usua,\n" +
                "  CASE WHEN ae.count_evidencias IS NULL THEN 'Sin evidencias asignadas' ELSE 'Tiene ' || ae.count_evidencias || ' evidencia/s asignada/s'\n" +
                "  END as evidencias FROM usuarios u JOIN persona per ON per.id_persona = u.persona_id_persona\n" +
                "JOIN usuariorol ur ON u.id = ur.usuario_id LEFT JOIN (SELECT usuario_id, COUNT(DISTINCT evidencia_id_evidencia) as count_evidencias\n" +
                "  FROM asignacion_evidencia ae_inner JOIN evidencia e_inner ON e_inner.id_evidencia = ae_inner.evidencia_id_evidencia\n" +
                "  JOIN indicador i_inner ON i_inner.id_indicador = e_inner.indicador_id_indicador\n" +
                "  JOIN asignacion_indicador po_inner ON po_inner.indicador_id_indicador = i_inner.id_indicador\n" +
                "  JOIN (SELECT MAX(id_modelo) AS max_id_modelo FROM modelo) max_mo ON po_inner.modelo_id_modelo = max_mo.max_id_modelo\n" +
                "  WHERE ae_inner.visible = true GROUP BY usuario_id) ae ON u.id = ae.usuario_id\n" +
                "WHERE ur.rol_rolid !=4 GROUP BY u.id, per.primer_nombre, per.primer_apellido, u.username, ae.count_evidencias;", nativeQuery = true)
        public List<ResponsableProjection> responsables();
        @Query(value = "SELECT u.* FROM public.usuarios u\n" +
                "WHERE ur.rol_rolid = 3 GROUP BY u.id, per.primer_nombre, per.primer_apellido, u.username, ae.count_evidencias;", nativeQuery = true)
        public List<ResponsableProjection> responsables();*/
        @Query(value = "SELECT\n" +
                "    u.id,\n" +
                "    per.primer_nombre || ' ' || per.primer_apellido AS nombres,\n" +
                "    u.username AS usua,\n" +
                "    r.rolnombre AS rol, \n" +
                "    CASE\n" +
                "        WHEN ae.count_evidencias IS NULL THEN 'Sin evidencias asignadas'\n" +
                "        ELSE 'Tiene ' || ae.count_evidencias || ' evidencia/s asignada/s'\n" +
                "    END AS evidencias,\n" +
                "    a.fecha_inicio AS fecha_inicio_actividad, \n" +
                "    a.fecha_fin AS fecha_fin_actividad\n" +
                "FROM\n" +
                "    usuarios u\n" +
                "JOIN\n" +
                "    persona per ON per.id_persona = u.persona_id_persona\n" +
                "JOIN\n" +
                "    usuariorol ur ON u.id = ur.usuario_id\n" +
                "JOIN\n" +
                "    roles r ON ur.rol_rolid = r.rolid\n" +
                "LEFT JOIN\n" +
                "    (\n" +
                "        SELECT\n" +
                "            usuario_id,\n" +
                "            COUNT(DISTINCT evidencia_id_evidencia) AS count_evidencias\n" +
                "        FROM\n" +
                "            asignacion_evidencia ae_inner\n" +
                "        JOIN\n" +
                "            evidencia e_inner ON e_inner.id_evidencia = ae_inner.evidencia_id_evidencia\n" +
                "        JOIN\n" +
                "            indicador i_inner ON i_inner.id_indicador = e_inner.indicador_id_indicador\n" +
                "        JOIN\n" +
                "            asignacion_indicador po_inner ON po_inner.indicador_id_indicador = i_inner.id_indicador\n" +
                "        JOIN\n" +
                "            (SELECT MAX(id_modelo) AS max_id_modelo FROM modelo) max_mo ON po_inner.modelo_id_modelo = max_mo.max_id_modelo\n" +
                "        WHERE\n" +
                "            ae_inner.visible = true\n" +
                "        GROUP BY\n" +
                "            usuario_id\n" +
                "    ) ae ON u.id = ae.usuario_id\n" +
                "LEFT JOIN\n" +
                "    actividad a ON u.id = a.usuario_id\n" +
                "WHERE\n" +
                "    r.rolnombre <> 'AUTORIDAD' \n" +
                "GROUP BY\n" +
                "    u.id,\n" +
                "    per.primer_nombre,\n" +
                "    per.primer_apellido,\n" +
                "    u.username,\n" +
                "    r.rolnombre, \n" +
                "    ae.count_evidencias,\n" +
                "    a.fecha_fin,\n" +
                "    a.fecha_inicio;", nativeQuery = true)
        public List<ResponsableProjection> responsables();

        @Modifying
        @Transactional
        @Query(value = "UPDATE actividad SET fecha_fin =:nuevaFecha WHERE usuario_id =:usuarioId", nativeQuery = true)
        void actualizarFechaFin(String nuevaFecha, Long usuarioId);

        @Query(value = "SELECT u.*\n" +
                        "FROM public.usuarios u\n" +
                        "JOIN public.usuariorol ur ON ur.usuario_id = u.id\n" +
                        "WHERE ur.rol_rolid = 1 AND u.visible=true;", nativeQuery = true)
        public List<Usuario> listaAdminDatos();


        @Query(value = "SELECT u.*,per.* FROM usuarios u " +
                "JOIN usuariorol ur ON u.id = ur.usuario_id " +
                "JOIN persona per on per.id_persona=u.persona_id_persona " +
                "WHERE ur.rol_rolid = 3 AND u.visible=true", nativeQuery = true)
        public List<Usuario> listaSOLORESPONSABLES();




}
