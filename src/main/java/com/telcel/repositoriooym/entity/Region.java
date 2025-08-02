package com.telcel.repositoriooym.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author marcos.hernandez
 */

@Data
@Entity
@Table(name = "regiones")
public class Region {

    /**
     * Identificador unico de la clase Region
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Long idRegion;

    @Column(nullable = false)
    private String nombre;
}
