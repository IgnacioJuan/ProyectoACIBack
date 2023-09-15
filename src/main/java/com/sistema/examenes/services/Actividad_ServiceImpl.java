package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.projection.ActivAprobadaProjection;
import com.sistema.examenes.projection.ActividadesProjection;
import com.sistema.examenes.repository.Actividad_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Actividad_ServiceImpl extends GenericServiceImpl<Actividad, Long> implements Actividad_Service {

    @Autowired
    private Actividad_repository repository;

    @Override
    public CrudRepository<Actividad, Long> getDao() {
        return repository;
    }
    @Override
    public List<Actividad> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Actividad> listar() {
        return repository.listarActividad();
    }

    @Override
    public List<ActivAprobadaProjection> actividadAtrasada() {
        return  repository.actividadRechazada();
    }

    @Override
    public List<ActivAprobadaProjection> actividadApr() {
        return repository.actividadAprobada();
    }

    @Override

    public List<Actividad> listaAtrasada() {
        return repository.listarActividadAtrasadas();
    }

    @Override
    public List<Actividad> listaCumplida() {
        return repository.listarActividadCumplidas();

    }
    @Override
    public List<Actividad> listarporusuario(String username) {
        return repository.listarporusuario(username);
    }
    @Override
    public List<Actividad>listarporEvidencia(Long idEvidencia ) {
        return repository.listarporEvidencia(idEvidencia);

    }
    
     @Override
    public List<Actividad> listaEvidAtrasada() {
        return repository.listarEvideRechazadasFecha();
    }

    @Override
    public List<Actividad> actividadUsu(Long id) {
        return repository.actividadUsu(id);
    }

    @Override
    public List<ActividadesProjection> actividadCont() {
        return repository.actividadCont();
    }

    public List<Actividad> listaActByUsuario(Long id) {
        return repository.listarByUsuario(id);
    }
    
}
