package com.sistema.examenes.services;

import com.sistema.examenes.entity.UsuarioRol;
import java.util.List;

public interface UsuarioRolService extends GenericService<UsuarioRol, Long>{

    public List<UsuarioRol> listarv();
    public UsuarioRol findByUsuario_UsuarioId(Long usuarioId);

}
