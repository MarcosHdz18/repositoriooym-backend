package com.telcel.repositoriooym.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "proyectos")
public class Proyecto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del proyecto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    /**
     * Atributo que identifica el calificativo del proyecto
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Atributo que identifica la fecha de publicacion
     */
    @Column(name = "fecha_liberacion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String fechaLiberacion;

    /**
     * Propiedad virtual que extrae el año de `fechaLiberacion`
     * si es un ISO_DATE válido; o devuelve null.
     */
    @Transient
    @JsonProperty("anio")
    public Integer getAnio() {
        if (fechaLiberacion == null) {
            return null;
        }
        // si guardaste "Pendiente" u otro texto, no intentamos parsear
        try {
            return LocalDate.parse(fechaLiberacion, DateTimeFormatter.ISO_DATE)
                    .getYear();
        } catch (DateTimeParseException ex) {
            // no era una fecha yyyy-MM-dd bien formateada
            return null;
        }
    }

    /**
     * Atributo que identifica los nodos del proyecto
     */
    @Lob
    @Column(name = "nodos", columnDefinition = "TEXT")
    private String nodos;

    /**
     * Ruta del documento F60
     */
    @Column(name = "f60", length = 512)
    private String f60;

    /**
     * Ruta del documento del diseño a bajo nivel
     */
    @Column(name = "lld", length = 512)
    private String lld;

    /**
     * Ruta del documento del diseño a alto nivel
     */
    @Column(name = "hld", length = 512)
    private String hld;

    /**
     * Ruta del documento del inventario logico
     */
    @Column(name = "layout", length = 512)
    private String layout;

    /**
     * Ruta del documento para el soporte del proyecto
     */
    @Column(name = "sla", length = 512)
    private String sla;

    /**
     * Ruta del documento del reporte de la instalacion fisica
     */
    @Column(name = "reporte_fotografico", length = 512)
    private String reporteFotografico;

    /**
     * Ruta del documento sobre la asignacion de fuerza y espacion en el proyecto
     */
    @Column(name = "asignacion_fuerza_espacio", length = 512)
    private String asignacionFuerzaEspacio;

    /**
     * Ruta del documento del inventario de hardware del proyecto
     */
    @Column(name = "inventario_hardware", length = 512)
    private String inventarioHardware;

    /**
     * Ruta del documento protocolo fisico sin firmar del proyecto
     */
    @Column(name = "atp_fisico", length = 512)
    private String atpFisico;

    /**
     * Ruta del documento protocolo fisico firmado del proyecto
     */
    @Column(name = "atp_fisico_firmado", length = 512)
    private String atpFisicoFirmado;

    /**
     * Ruta del documento protocolo logico sin firmar del proyecto
     */
    @Column(name = "atp_logico", length = 512)
    private String atpLogico;

    /**
     * Ruta del documento protocolo logico firmado del proyecto
     */
    @Column(name = "atp_logico_firmado", length = 512)
    private String atpLogicoFirmado;

    /**
     * Ruta del documento reporte de transferencia operativa del proyecto
     */
    @Column(name = "reporte_transferencia_operativa", length = 512)
    private String reporteTransferenciaOperativa;

    /**
     * Ruta del documento carta responsiva del area de iaas del proyecto
     */
    @Column(name = "carta_responsiva_iaas", length = 512)
    private String cartaResponsivaIaaS;

    /**
     * Ruta del documento carta responsiva del area de plataforma del proyecto
     */
    @Column(name = "carta_responsiva_plataforma", length = 512)
    private String cartaResponsivaPlataforma;

    /**
     * Ruta del documento carta responsiva del area de storage del proyecto
     */
    @Column(name = "carta_responsiva_storage", length = 512)
    private String cartaResponsivaStorage;

    /**
     * Ruta del documento carta responsiva del area de alta disponibilidad del proyecto
     */
    @Column(name = "carta_responsiva_ha", length = 512)
    private String cartaResponsivaHa;

    /**
     * Ruta del documento carta responsiva del area de gsoc del proyecto
     */
    @Column(name = "carta_responsiva_gsoc", length = 512)
    private String cartaResponsivaGsoc;

    /**
     * Ruta del documento otro
     */
    @Column(name = "otros", length = 512)
    private String otros;

    /**
     * Responsable de recepcion del proyecto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_responsable")
    private Responsable responsableProyecto;

    /**
     * Tipo de proyecto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_tipo_proyecto", nullable = false)
    private TipoProyecto tipoProyecto;
}
