package com.telcel.repositoriooym.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "tipos_proyecto")
public class TipoProyecto implements Serializable {

    /**
     * Identificador de la clase TipoProyecto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_proyecto")
    private Long idTipoProyecto;

    /**
     * Nombre del tipo del proyecto
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Descripcion del tipo de proyecto
     */
    @Column(name = "descripcion")
    private String descripcion;
}
