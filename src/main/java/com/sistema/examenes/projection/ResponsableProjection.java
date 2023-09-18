package com.sistema.examenes.projection;

import java.util.Date;

public interface ResponsableProjection {
    Long getId();
    String getNombres();

    String getRol();

    Date getFecha_fin();
    Date getFecha_inicio();
    String getUsua();
    String getEvidencias();
}
