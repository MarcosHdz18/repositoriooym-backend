package com.telcel.repositoriooym.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author marcos.hernandez
 */

@Data
@Entity
@Table(name = "responsable_proyecto")
public class ResponsableProyecto {

    /**
     * Identificador unico de la clase ResponsableProyecto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsable_proyecto")
    private Long idResponsableProyecto;

    /**
     * Nombre del responsable del proyecto
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Apellido paterno del responsable del proyecto
     */
    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    /**
     * Apellido materno del responsable del proyecto
     */
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    /**
     * Numero de empleado del responsable del proyecto
     */
    @Column(name = "numero_empleado")
    private Integer numeroEmpleado;

    /**
     * Area a la que pertenecen los responsables de proyecto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AreaResponsableProyecto areaResponsableProyecto;

    /**
     * Serialization del objeto
     */
    private static final long serialVersionUID = -2932582802203305841L;
}
