package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.TipoProyecto;
import com.telcel.repositoriooym.response.TipoProyectoResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * @author marcos.hernandez
 */
public interface ITipoProyectoService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    public ResponseEntity<TipoProyectoResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idTipoProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    public ResponseEntity<TipoProyectoResponseRest> findById(Long idTipoProyecto);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param tipoProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    public ResponseEntity<TipoProyectoResponseRest> save(TipoProyecto tipoProyecto);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param tipoProyecto objeto que se actualizara en la base de datos
     * @param idTipoProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    public ResponseEntity<TipoProyectoResponseRest> update(TipoProyecto tipoProyecto, Long idTipoProyecto);

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idTipoProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    public ResponseEntity<TipoProyectoResponseRest> deleteById(Long idTipoProyecto); 

}
