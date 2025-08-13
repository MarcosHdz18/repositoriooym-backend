package com.telcel.repositoriooym.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author marcos.hernandez
 */

public interface ISubirArchivoService {

    void init() throws IOException;

    /**
     * Metodo que realiza la carga del archivo en la URL
     * @param filename nombre del archivo a cargar
     * @return Objeto de tipo Resource
     * @throws MalformedURLException Excepcion de tipo MalformedURLException
     */
    public Resource cargarArchivo(String filename) throws MalformedURLException;

    /**
     * Metodo que realiza la copia del archivo a la carpeta destino del proyecto
     * @param file archivo que se copiara a la carpeta destino
     * @return String
     * @throws IOException Excepcion de tipo IOException
     */
    public String copiarArchivo(MultipartFile file) throws IOException;

    /**
     * Metodo que realiza la comprobacion del borrado del archivo
     * @param filename nombre del archivo
     * @return tipo boolean
     */
    public boolean borrarArchivo(String filename);

    /**
     * Metodo que realiza la copia del archivo a la carpeta destino del proyecto
     * @param folderName subcarpeta
     * @param file archivo a copiar
     * @return String como tipo de dato para almacenar el path
     * @throws IOException
     */
    public String copiarArchivoEnSubCarpeta(String folderName, MultipartFile file, boolean overwrite) throws IOException;
}
