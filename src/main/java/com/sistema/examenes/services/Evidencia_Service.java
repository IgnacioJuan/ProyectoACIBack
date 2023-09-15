package com.sistema.examenes.services;

import com.sistema.examenes.entity.Actividad;
import com.sistema.examenes.entity.Evidencia;
import com.sistema.examenes.projection.AsigEvidProjection;
import com.sistema.examenes.projection.EvidenciaCalProjection;
import com.sistema.examenes.projection.EvidenciaProjection;
import com.sistema.examenes.projection.EvidenciasProjection;

import java.util.List;

public interface Evidencia_Service extends GenericService<Evidencia, Long> {
    public List<Evidencia> listar();

    public List<Evidencia> evidenciaUsuario(String username);
    public List<Evidencia> listarEvidenciaAsigna(Long idUsuario) ;

    List<Evidencia> listarEvidenciaPorIndicador(Long id_indicador);
    List<Evidencia> evidenciacriterio(Long idcriterio);
    List<EvidenciasProjection> evidenciaAprobada(Long id_modelo);
    List<EvidenciasProjection> evidenciaRechazada(Long id_modelo);
    EvidenciaCalProjection evidenciacal(Long id_evidencia, Long id_modelo);
    List<AsigEvidProjection> evidenciatab(Long idcriterio);
    List<AsigEvidProjection> listarEvidenciaAdmin(Long idUser);
    public List<EvidenciaProjection> evidenUsuario(String username);
}
