package com.sistema.examenes.projection;

public interface IndicadorResp {
    Long getId_indicador();
    String getNombre();
    String getDescripcion();
    Double getPeso();
    Double getValor_obtenido();
    Double getPorc_obtenido();
    Double getEstandar();
    String getTipo();
    String getRespon();
}
