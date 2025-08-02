package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Region;
import com.telcel.repositoriooym.entity.Sitio;
import com.telcel.repositoriooym.response.RegionResponseRest;
import com.telcel.repositoriooym.response.SitioResponseRest;
import org.springframework.http.ResponseEntity;

public interface ISitioService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    public ResponseEntity<SitioResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idSitio identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    public ResponseEntity<SitioResponseRest> findById(Long idSitio);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param sitio objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    public ResponseEntity<SitioResponseRest> save(Sitio sitio, Long regionId);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param sitio objeto que se actualizara en la base de datos
     * @param idSitio identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    public ResponseEntity<SitioResponseRest> update(Sitio sitio, Long idSitio, Long regionId);
    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idSitio identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    public ResponseEntity<SitioResponseRest> deleteById(Long idSitio);

}
