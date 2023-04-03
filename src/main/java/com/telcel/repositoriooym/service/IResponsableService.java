package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Responsable;
import com.telcel.repositoriooym.response.ResponsableResponseRest;
import org.springframework.http.ResponseEntity;

public interface IResponsableService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idResponsableProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableResponseRest> findById(Long idResponsableProyecto);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param responsableProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableResponseRest> save(Responsable responsableProyecto, Long areaId);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param responsableProyecto objeto que se actualizara en la base de datos
     * @param idResponsableProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableResponseRest> update(Responsable responsableProyecto, Long areaId, Long idResponsableProyecto);

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idResponsableProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableResponseRest> deleteById(Long idResponsableProyecto);
    
}
