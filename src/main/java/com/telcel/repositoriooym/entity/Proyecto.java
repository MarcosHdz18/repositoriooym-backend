package com.telcel.repositoriooym.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;



}
