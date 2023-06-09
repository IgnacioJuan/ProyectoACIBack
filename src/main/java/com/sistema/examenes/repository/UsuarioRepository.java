package com.sistema.examenes.repository;

import com.sistema.examenes.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        @Query(value = "SELECT *\n" +
                        "\tFROM usuarios WHERE username = :username AND visible=true", nativeQuery = true)
        public Usuario findByUsername(String username);

        @Query(value = "SELECT *\n" +
                        "\tFROM usuarios WHERE username = :username", nativeQuery = true)
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

        @Query(value = "SELECT *\n" +
                        "FROM usuarios u\n" +
                        "JOIN asignacion_evidencia ae ON u.id = ae.usuario_id\n" +
                        "JOIN persona p  ON u.persona_id_persona = p.id_persona\n" +
                        "WHERE u.visible = true AND ae.visible = true;", nativeQuery = true)
        public List<Usuario> listaResponsablesDatos();

        @Query(value = "SELECT u.* " +
                        "FROM usuarios u " +
                        "JOIN usuariorol ur ON u.id = ur.usuario_id " +
                        "LEFT JOIN asignacion_evidencia ae ON u.id = ae.usuario_id " +
                        "WHERE ur.rol_rolid = 3 AND ae.id_asignacion_evidencia IS NULL AND u.visible=true", nativeQuery = true)
        public List<Usuario> listaResponsablesAdmin();

        @Query(value = "SELECT u.*\n" +
                        "FROM public.usuarios u\n" +
                        "JOIN public.usuariorol ur ON ur.usuario_id = u.id\n" +
                        "WHERE ur.rol_rolid = 1\n" +
                        "AND u.visible=true;", nativeQuery = true)
        public List<Usuario> listaAdminDatos();
}
