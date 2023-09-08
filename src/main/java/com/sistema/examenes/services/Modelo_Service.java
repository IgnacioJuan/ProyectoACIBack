package com.sistema.examenes.services;

import com.sistema.examenes.entity.Modelo;
import com.sistema.examenes.projection.ModelIndiProjection;
import com.sistema.examenes.projection.ModeloVistaProjection;
import com.sistema.examenes.projection.SubcriterioIndicadoresProjectionFull;
import com.sistema.examenes.projection.criteriosdesprojection;

import java.util.List;

public interface Modelo_Service extends GenericService<Modelo, Long> {
    public List<Modelo> listar();

    public Modelo listarMaximo();

    public List<Modelo> listarModeloExcepto(Long id_modelo);

    public List<ModeloVistaProjection> obtenerDatosModelo();
    public List<ModelIndiProjection> listindiModelo(Long id_modelo);
    public List<criteriosdesprojection> listicritedes(Long id_modelo,String nombre);
    public List<criteriosdesprojection> listicrinom(Long id_modelo, String nomcrite);
    List<criteriosdesprojection> criterioadmin(Long id_modelo,Long id);
    Boolean existefecha(String inicio,String fin);
    Boolean fechaeditar(String inicio,String fin);
}
