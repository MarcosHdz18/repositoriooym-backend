package com.telcel.repositoriooym.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author marcos.hernandez
 */
@Data
@Entity
@Table(name = "documentos_adjuntos")
public class DocumentoAdjunto {

    /**
     * Identificador del documento adjunto del proyecto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_adjunto")
    private Long idDocumentoAdjunto;

    /**
     * Nombre del archivo adjunto
     */
    @Column(name = "nombre_archivo", length = 512)
    private String nombreArchivo;

    /**
     * Ruta del archivo adjunto
     */
    @Column(name = "ruta_archivo", length = 512)
    private String rutaArchivo;

    /**
     * Usuario que carga el o los archivos
     */
    @Column(name = "usuario")
    private String usuarioSubio;

    /**
     * Fecha de carga en que se hizo la carga
     */
    @Column(name = "fecha_carga", length = 512)
    private LocalDateTime fechaCarga =  LocalDateTime.now();

    /**
     * Proyecto al que estara relacionado el archivo adjunto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto")
    @JsonIgnore
    private Proyecto proyecto;

}
