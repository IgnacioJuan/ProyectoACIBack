package com.sistema.examenes.services;

import com.sistema.examenes.entity.Usuario;
import com.sistema.examenes.projection.ResponsableProjection;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioService extends GenericService<Usuario, Long> {
    public Usuario obtenerUsuario(String username);

    public Usuario obtenerId(String username);

    public Usuario findAllByUsername(String username);

    public List<ResponsableProjection> responsables();
    public List<Usuario> listaAdminDatos();

    public Boolean listapersonasusuario(Long usuarioId);
    void actualizarPersonaIdEnUsuario(Long usuarioId);
}
