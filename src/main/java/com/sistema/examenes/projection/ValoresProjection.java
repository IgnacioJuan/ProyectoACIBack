package com.sistema.examenes.projection;

import java.math.BigDecimal;

public interface ValoresProjection {
    String getNomcriterio();
    BigDecimal getPonderacio();
    BigDecimal getVlObtenido();
    BigDecimal getVlobtener();
}
