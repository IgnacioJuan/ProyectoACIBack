package com.sistema.examenes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "asignacion_evidencia")
public class Asignacion_Evidencia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_evidencia")
    private Long id_asignacion_evidencia;

    private boolean visible;
    @Column(name = "id_modelo")
    private Long id_modelo;
    @Column(name = "fecha_inicio")
    private Date fecha_inicio;
    @Column(name = "fecha_fin")
    private Date fecha_fin;
    @ManyToOne(fetch = FetchType.EAGER)
    private Evidencia evidencia;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
}
