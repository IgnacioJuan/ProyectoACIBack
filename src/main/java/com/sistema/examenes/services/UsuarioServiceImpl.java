package com.sistema.examenes.services;

import com.sistema.examenes.entity.Usuario;
import com.sistema.examenes.projection.ResponsableProjection;
import com.sistema.examenes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Long> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public CrudRepository<Usuario, Long> getDao() {
        return usuarioRepository;
    }

    @Override
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }
    @Override
    public Usuario findAllByUsername(String username) {
        return usuarioRepository.findAllByUsername(username);
    }

    @Override
    public List<ResponsableProjection> responsables() {
        return usuarioRepository.responsables();
    }


    @Override
    public Usuario obtenerId(String username) {
        return usuarioRepository.buscarId(username);
    }

    @Override
    public List<Usuario> listaAdminDatos() {
        return usuarioRepository.listaAdminDatos();
    }
    @Override
    public List<Usuario> listaSOLORESPONSABLES() {
        return usuarioRepository.listaSOLORESPONSABLES();
    }

}