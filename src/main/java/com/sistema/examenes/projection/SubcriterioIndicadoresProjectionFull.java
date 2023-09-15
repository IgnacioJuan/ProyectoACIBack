package com.sistema.examenes.projection;

public interface SubcriterioIndicadoresProjectionFull {
    Long getId_subcriterio();
    String getNombre();
    String getDescripcion();
    boolean isVisible();
    Long getCantidadIndicadores();
    String getNombreCriterio();
}
