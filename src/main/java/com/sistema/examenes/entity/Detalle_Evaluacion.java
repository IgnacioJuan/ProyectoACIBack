package com.sistema.examenes.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "detalle_evaluacion")
public class Detalle_Evaluacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_evaluacion")
    private Long id_detalle_evaluacion;
    @Column(name = "estado")
    private boolean estado;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha")
    private Date fecha;
    //Columna para el eliminado logico no borrar
    @Column(name = "visible")
    private boolean visible;
    @Column(name = "id_modelo")
    private Long id_modelo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.EAGER)
    private Evidencia evidencia;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
