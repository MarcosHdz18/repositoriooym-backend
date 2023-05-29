package com.telcel.repositoriooym.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "proyectos")
public class Proyecto implements Serializable {

    /**
     * Identificador del proyecto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    /**
     * Nombre del proyecto
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Fecha de liberacion del proyecto
     */
    @Column(name = "fecha_liberacion")
    private Date fechaLiberacion;

    /**
     * Documento f60 del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "f60", length = 1000)
    private byte[] f60;

    /**
     * Low Level Design del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "lld", length = 1000)
    private byte[] lld;

    /**
     * High Level Design del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "hld", length = 1000)
    private byte[] hld;

    /**
     * Instructivos de Atencion de Alarmas del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "instructivos_alarmas", length = 1000)
    private byte[] instructivosAlarmas;

    /**
     * Politicas de respaldo del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "politicas_backups", length = 1000)
    private byte[] politicasBackups;

    /**
     * Rutinas de mantenimiento del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "rutinas_mantenimiento", length = 1000)
    private byte[] rutinasMantenimiento;

    /**
     * Proceso renovacion de licencias del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "renovacion_licencias", length = 1000)
    private byte[] renovacionLicencias;

    /**
     * Manuales de operacion del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "manuales_operacion", length = 1000)
    private byte[] manualesOperacion;

    /**
     * Matriz de trazabilidad del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "matriz_trazabilidad", length = 1000)
    private byte[] matrizTrazabilidad;

    /**
     * Layout del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "layout", length = 1000)
    private byte[] layout;

    /**
     * SLA del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "sla", length = 1000)
    private byte[] sla;

    /**
     * Formato de filtrado del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "formato_filtrado", length = 1000)
    private byte[] formatoFiltrado;

    /**
     * Reporte fotografico del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "reporte_fotografico", length = 1000)
    private byte[] reporteFotografico;

    /**
     * Asignacion de fuerza de los equipos del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "asignacion_fuerza", length = 1000)
    private byte[] asignacionFuerza;

    /**
     * Asignacion de espacio de los equipos del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "asignacion_espacio", length = 1000)
    private byte[] asignacionEspacio;

    /**
     * Inventario de Hardware de los equipos del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "inventario_hardware", length = 1000)
    private byte[] inventarioHardware;

    /**
     * Nodos o equipos del proyecto
     */
    @Column(name = "nodos")
    private String nodos;

    /**
     * Protocolo de aceptacion fisico del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "atp_fisico", length = 1000)
    private byte[] atpFisico;

    /**
     * Protocolo de aceptacion logico del proyecto
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "atp_logico", length = 1000)
    private byte[] atpLogico;

    /**
     * Reporte de transferencia operativa
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "rto", length = 1000)
    private byte[] rto;

    /**
     * Carta responsiva de los accesos del proyecto para el area de IAAS
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "carta_responsiva_iaas", length = 1000)
    private byte[] cartaResponsivaIAAS;

    /**
     * Carta responsiva de los accesos del proyecto para el area de Plataforma
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "carta_responsiva_plataforma", length = 1000)
    private byte[] cartaResponsivaPlataforma;

    /**
     * Carta responsiva de los accesos del proyecto para el area de Storage
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "carta_responsiva_storage", length = 1000)
    private byte[] cartaResponsivaStorage;

    /**
     * Carta responsiva de los accesos del proyecto para el area del GSOC
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "carta_responsiva_gsoc", length = 1000)
    private byte[] cartaResponsivaGSOC;

    /**
     * Carta responsiva de los accesos del proyecto para el area de HA
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "carta_responsiva_ha", length = 1000)
    private byte[] cartaResponsivaHA;

    /**
     * Responsable de Recepcion del proyecto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Responsable responsableProyecto;

    private static final long serialVersionUID = 1540440495794585232L;
}
