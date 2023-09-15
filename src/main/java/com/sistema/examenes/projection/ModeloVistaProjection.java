package com.sistema.examenes.projection;

import java.sql.Date;
import java.sql.Timestamp;

public interface ModeloVistaProjection {
    Long getId_modelo();
    String getNombre();
    Timestamp getFecha_fin();
    Timestamp  getFecha_final_act();
    Timestamp  getFecha_inicio();
    Long getNro_indicadores();
    Long getNro_subcriterios();
    Long getNro_criterios();
}
