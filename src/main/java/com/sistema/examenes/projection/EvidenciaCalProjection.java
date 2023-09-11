package com.sistema.examenes.projection;

import java.math.BigDecimal;

public interface EvidenciaCalProjection {
    String getTipo();
    Long getId_in();
    BigDecimal getPeso();
    String getDescrip();
    String getEst();
}
