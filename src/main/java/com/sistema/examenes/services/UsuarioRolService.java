package com.sistema.examenes.services;

import com.sistema.examenes.entity.Subcriterio;
import com.sistema.examenes.entity.UsuarioRol;
import com.sistema.examenes.projection.CriteUsuarioProjection;

import java.util.List;

public interface UsuarioRolService extends GenericService<UsuarioRol, Long>{

    public List<UsuarioRol> listarv();
    public UsuarioRol findByUsuario_UsuarioId(Long usuarioId);
    List<CriteUsuarioProjection> actividadesusuario(Long id, Long id_modelo);
    List<CriteUsuarioProjection> evidenusuario(Long id, Long id_modelo);
}
