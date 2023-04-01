package com.telcel.repositoriooym.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author marcos.hernandez
 */

@Data
@Entity
@Table(name = "area_responsable")
public class AreaResponsableProyecto {

    /**
     * Identificador unico de la clase AreaResponsableProyecto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Long idAreaResponsableProyecto;

    /**
     * Nombre del area del responsable del proyecto
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Descripcion del area del responsable del proyecto
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Serializacion del objeto
     */
    private static final long serialVersionUID = -7081468696470485992L;
}
