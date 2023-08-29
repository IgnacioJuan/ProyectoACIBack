package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Notificacion;
import com.sistema.examenes.repository.Notificacion_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class Notificacion_ServiceImpl extends GenericServiceImpl<Notificacion,Long> implements NotificacionService {
    @Autowired
    private Notificacion_repository notificacionRepository;
    @Override
    public CrudRepository<Notificacion, Long> getDao() {
        return notificacionRepository;
    }

    @Override
    public List<Notificacion> listar(Long user) {
        return notificacionRepository.listarUserNoti(user);
    }


    @Override
    public void eliminar(Long id) {
        notificacionRepository.borrar(id);
    }

    @Override
    public List<Notificacion> listarNotifi(String fecha) {
        return notificacionRepository.listarNot(fecha);
    }

    @Override
    public List<Notificacion> listarulNoti(Long us) {
        return notificacionRepository.listarulNoti(us);
    }

    @Override
    public List<Notificacion> all(String roluser) {
        return notificacionRepository.all(roluser);
    }

    @Override
    public List<Notificacion> all2(String roluser) {
        return notificacionRepository.all2(roluser);
    }

}
