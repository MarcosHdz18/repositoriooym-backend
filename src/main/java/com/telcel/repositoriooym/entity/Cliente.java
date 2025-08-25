package com.telcel.repositoriooym.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    /**
     * Identificador unico de la clase Cliente
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    /**
     * Nombre del area final a la que se entrega la plataforma
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Descripcion del area final a la que se le entrega la plataforma
     */
    @Column(name = "descripcion")
    private String descripcion;
}
