package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.response.AreaResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * @author marcos.hernandez
 */
public interface IAreaService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idAreaResponsableProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponseRest> findById(Long idAreaResponsableProyecto);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param areaResponsableProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponseRest> save(Area areaResponsableProyecto);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param areaResponsableProyecto objeto que se actualizara en la base de datos
     * @param idAreaResponsableProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponseRest> update(Area areaResponsableProyecto, Long idAreaResponsableProyecto);

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idAreaResponsableProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo AreaResponsableProyectoResponseRest
     */
    public ResponseEntity<AreaResponseRest> deleteById(Long idAreaResponsableProyecto);
}
