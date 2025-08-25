package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Cliente;
import com.telcel.repositoriooym.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * @author marcos.hernandez
 */
public interface IClienteService {

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    public ResponseEntity<ClienteResponseRest> findAll();

    /**
     * Metodo que realiza la busqueda por id
     * @param idCliente identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    public ResponseEntity<ClienteResponseRest> findById(Long idCliente);

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param cliente objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    public ResponseEntity<ClienteResponseRest> save(Cliente cliente);

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param cliente objeto que se actualizara en la base de datos
     * @param idCliente identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    public ResponseEntity<ClienteResponseRest> update(Cliente cliente, Long idCliente);

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idCliente identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    public ResponseEntity<ClienteResponseRest> deleteById(Long idCliente);
}
