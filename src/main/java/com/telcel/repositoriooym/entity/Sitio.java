package com.telcel.repositoriooym.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author marcos.hernandez
 */

@Data
@Entity
@Table(name = "sitios")
public class Sitio {

    // Identificador unico de la clase Sitio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sitio")
    private Long idSitio;

    // Nombre del sitio
    @Column(nullable = false)
    private String nombre;

    // Codigo del sitio
    @Column(nullable = false)
    private String treeChar;

    // Direccion del sitio
    @Column(name = "direccion")
    private String direccion;

    // Nombre del contacto del sitio
    @Column(name = "nombre_contacto", nullable = false)
    private String nombreContacto;

    // Telefono del contacto del sitio
    @Column(name = "telefono_contacto", nullable = false)
    private String telefonoContacto;

    // Correo del contacto del sitio
    @Column(name = "correo_contacto", nullable = false)
    private String correoContacto;

    // Region a la que pertenece el sitio en estructura jerarquica con base en la regla del negocio
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;
}
