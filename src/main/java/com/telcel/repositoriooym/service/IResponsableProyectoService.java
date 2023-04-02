package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.ResponsableProyecto;
import com.telcel.repositoriooym.response.ResponsableProyectoResponse;
import com.telcel.repositoriooym.response.ResponsableProyectoResponseRest;
import org.springframework.http.ResponseEntity;

public interface IResponsableProyectoService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableProyectoResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idResponsableProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableProyectoResponseRest> findById(Long idResponsableProyecto);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param responsableProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableProyectoResponseRest> save(ResponsableProyecto responsableProyecto);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param responsableProyecto objeto que se actualizara en la base de datos
     * @param idResponsableProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableProyectoResponseRest> update(ResponsableProyecto responsableProyecto, Long idResponsableProyecto);

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idResponsableProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo ResponsableProyectoResponseRest
     */
    public ResponseEntity<ResponsableProyectoResponseRest> deleteById(Long idResponsableProyecto);
    
}
