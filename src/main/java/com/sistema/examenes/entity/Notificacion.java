package com.sistema.examenes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity
@Getter
@Setter
@Table(name = "notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuario;
    private String rol;
    @Size(max = 600)
    private String mensaje;

    @Column(name = "fecha", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private Boolean visto;

}
