package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.service.ISubirArchivoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * @author marcos.hernandez
 */

@Service
public class SubirArchivoServiceImpl implements ISubirArchivoService {

    /**
     * Carpeta donde se almacenaran los archivos a subir
     */
    @Value("${folder.location}")
    private String documentacionProyectos;

    /**
     * Path de la carpeta donde se almacenan los archivos
     */
    private Path rootLocation;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(documentacionProyectos);
        Files.createDirectories(rootLocation);
    }

    /**
     * @param filename nombre del archivo a cargar
     * @return objeto de tipo Resource
     * @throws MalformedURLException
     */
    @Override
    public Resource cargarArchivo(String filename) throws MalformedURLException {
        Path path = getPath(filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Error en el path: " + path.toString());
        }
        return resource;
    }

    /**
     * @param file archivo que se copiara a la carpeta destino
     * @return String
     * @throws IOException
     */
    @Override
    public String copiarArchivo(MultipartFile file) throws IOException {
        // Copiar archivo con un prefijo aleatorio en el mismo
        // String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String uniqueFilename = file.getOriginalFilename();
        Path rootPath = getPath(uniqueFilename);
        Files.copy(file.getInputStream(), rootPath);
        return uniqueFilename;
    }

    /**
     * @param filename nombre del archivo
     * @return boolean
     */
    @Override
    public boolean borrarArchivo(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();

        if (file.exists() && file.canRead()) {
            if (file.delete()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo que realiza la copia del archivo a la carpeta destino del proyecto
     *
     * @param folderName subcarpeta
     * @param file       archivo a copiar
     * @return String como tipo de dato para almacenar el path
     * @throws IOException
     */
    @Override
    public String copiarArchivoEnSubCarpeta(String folderName, MultipartFile file, boolean overwrite) throws IOException {

        // Sanitizar el nombre de la carpeta
        String safeName = folderName.trim().replaceAll("[\\\\/:*?\"<>| ]+", "_").toUpperCase();

        // Crea la carpeta dentro de documentacion_proyectos/<folderName> y aseguramos su existencia
        Path projectFolder = this.rootLocation.resolve(safeName);
        Files.createDirectories(projectFolder);

        // Nombre unico (o se usa el original)
        String filename = file.getOriginalFilename();
        Path destination = projectFolder.resolve(filename).toAbsolutePath();

        if (overwrite) {
            // Sobreescribe el archivo en la carpeta creada con anterioridad
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } else {
            // Copia el archivo en la carpeta creada con anterioridad
            Files.copy(file.getInputStream(), destination);
        }

        // Devuelve la ruta relativa para guardar en BD: "folderName/filename"
        return safeName + "/" +filename;
    }

    /**
     * Metodo que obtiene el path del archivo
     * @param filename nombre del archivo
     * @return objeto de tipo Path
     */
    public Path getPath(String filename) {
        return Paths.get(documentacionProyectos).resolve(filename).toAbsolutePath();
    }
}
