package com.sistema.examenes.projection;

public interface IndicadorEvidenciasProjectionFull {
    Long getId_indicador();
    String getNombre();
    String getCriterio();
    String getSubcriterio();
    String getEvidencia();
    String getDescripcion();
    double getPeso();
    double getEstandar();
    String getTipo();
    double getValor_obtenido();
    double getPorc_obtenido();
    double getPorc_utilida_obtenida();
    boolean isVisible();
    Long getCantidadEvidencia();
    String getNombreSubcriterio();
}
