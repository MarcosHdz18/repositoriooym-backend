package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Region;
import com.telcel.repositoriooym.response.RegionResponseRest;
import org.springframework.http.ResponseEntity;

public interface IRegionService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    public ResponseEntity<RegionResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idRegion identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    public ResponseEntity<RegionResponseRest> findById(Long idRegion);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param region objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    public ResponseEntity<RegionResponseRest> save(Region region);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param region objeto que se actualizara en la base de datos
     * @param idRegion identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    public ResponseEntity<RegionResponseRest> update(Region region, Long idRegion);
    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idRegion identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    public ResponseEntity<RegionResponseRest> deleteById(Long idRegion);
}
