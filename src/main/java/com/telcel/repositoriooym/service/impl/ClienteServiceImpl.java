package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.entity.Cliente;
import com.telcel.repositoriooym.repository.IClienteRepository;
import com.telcel.repositoriooym.response.AreaResponseRest;
import com.telcel.repositoriooym.response.ClienteResponseRest;
import com.telcel.repositoriooym.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author marcos.hernandez
 */
@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;


    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteResponseRest> findAll() {

        ClienteResponseRest response = new ClienteResponseRest();

        try {
            List<Cliente> clientes = (List<Cliente>) this.clienteRepository.findAll();

            response.getClienteResponse().setClientes(clientes);
            response.setMetadata("Respuesta exitosa", "00", "Lista de clientes");

        } catch (Exception e) {
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda por id
     * @param idCliente identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteResponseRest> findById(Long idCliente) {

        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> clientes = new ArrayList<>();

        try {
            Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);

            if (cliente.isPresent()) {
                clientes.add(cliente.get());
                response.getClienteResponse().setClientes(clientes);
                response.setMetadata("Respuesta exitosa", "00", "Cliente encontrado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Cliente no encontrado");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al consultar el registro");
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     * @param cliente objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> save(Cliente cliente) {

        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> clientes = new ArrayList<>();

        try {
            Cliente clienteSaved = this.clienteRepository.save(cliente);

            if (clienteSaved != null) {
                clientes.add(clienteSaved);
                response.getClienteResponse().setClientes(clientes);
                response.setMetadata("Respuesta exitosa", "00", "Cliente guardado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Cliente no guardado por malformación del request");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al guardar el registro");
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     * @param cliente   objeto que se actualizara en la base de datos
     * @param idCliente identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    @Override
    public ResponseEntity<ClienteResponseRest> update(Cliente cliente, Long idCliente) {

        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> clientes = new ArrayList<>();

        try {
            Optional<Cliente> clienteUpdate = this.clienteRepository.findById(idCliente);

            if (clienteUpdate.isPresent()) {
                // Actualizacion del registro
                clienteUpdate.get().setNombre(cliente.getNombre());
                clienteUpdate.get().setDescripcion(cliente.getDescripcion());

                Cliente clienteToUpdate = this.clienteRepository.save(clienteUpdate.get());

                if (clienteToUpdate != null) {
                    clientes.add(clienteToUpdate);

                    response.getClienteResponse().setClientes(clientes);
                    response.setMetadata("Respuesta exitosa", "00", "Cliente actualizado con éxito");
                } else {
                    response.setMetadata("Respuesta fallida","-1","Cliente no actualizado por malformación del request");
                    return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                response.setMetadata("Respuesta fallida", "-1", "Cliente no encontrado para actualizar");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al actualizar el registro");
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     * @param idCliente identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo ClienteResponseRest
     */
    @Override
    public ResponseEntity<ClienteResponseRest> deleteById(Long idCliente) {

        ClienteResponseRest response = new ClienteResponseRest();

        try {

            Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);

            if (cliente.isPresent()) {
                this.clienteRepository.deleteById(idCliente);
                response.setMetadata("Respues exitosa", "00", "Se ha eliminado el registro con id " + idCliente + " exitosamente");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Cliente no encontrado");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al eliminar el registro");
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }
}
