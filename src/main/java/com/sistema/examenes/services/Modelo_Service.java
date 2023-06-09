package com.sistema.examenes.services;

import com.sistema.examenes.entity.Modelo;

import java.util.List;

public interface Modelo_Service extends GenericService<Modelo, Long> {
    public List<Modelo> listar();

    public Modelo listarMaximo();

    public List<Modelo> listarModeloExcepto(Long id_modelo);

}
