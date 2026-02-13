package com.telcel.repositoriooym.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "bitacoras")
public class Bitacora {

    /**
     * Identificador de la bitacora
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;
    private String accion;
    private String usuario;
    private String detalle;
    private LocalDateTime fecha = ZonedDateTime.now(ZoneId.of("America/Mexico_City")).toLocalDateTime();
}
