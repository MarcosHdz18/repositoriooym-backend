package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.entity.TipoProyecto;
import com.telcel.repositoriooym.repository.ITipoProyectoRepository;
import com.telcel.repositoriooym.response.TipoProyectoResponseRest;
import com.telcel.repositoriooym.service.ITipoProyectoService;
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
public class TipoProyectoServiceImpl implements ITipoProyectoService {

    // Inyección de dependencias con anotación de jackarta
    @Autowired
    private ITipoProyectoRepository tipoProyectoRepository;


    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     *
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<TipoProyectoResponseRest> findAll() {

        TipoProyectoResponseRest respuesta = new TipoProyectoResponseRest();

        try {
            List<TipoProyecto> tiposProyecto = (List<TipoProyecto>) this.tipoProyectoRepository.findAll();

            respuesta.getTipoProyectoResponse().setTiposProyecto(tiposProyecto);

            respuesta.setMetadata("Respuesta exitosa", "00", "Lista de los Tipos de Proyecto que existen");
        } catch (Exception e) {
            respuesta.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda por id
     *
     * @param idTipoProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<TipoProyectoResponseRest> findById(Long idTipoProyecto) {

        TipoProyectoResponseRest respuesta = new TipoProyectoResponseRest();
        List<TipoProyecto> tiposProyectos = new ArrayList<>();

        try {
            Optional<TipoProyecto> tipoProyecto = this.tipoProyectoRepository.findById(idTipoProyecto);

            if (tipoProyecto.isPresent()) {
                tiposProyectos.add(tipoProyecto.get());
                respuesta.getTipoProyectoResponse().setTiposProyecto(tiposProyectos);
                respuesta.setMetadata("Respuesta exitosa", "00", "Tipos de proyectos encontrados con éxito");
            } else {
                respuesta.setMetadata("Respuesta fallida", "-1", "Tipo de proyecto no encontrado con ese id");
                return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            respuesta.setMetadata("Error en la respuesta", "-1", "Error al consultar los registros");
            e.getStackTrace();
            return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     *
     * @param tipoProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    @Override
    public ResponseEntity<TipoProyectoResponseRest> save(TipoProyecto tipoProyecto) {

        TipoProyectoResponseRest respuesta = new TipoProyectoResponseRest();
        List<TipoProyecto> tiposProyecto = new ArrayList<>();

        try {
            TipoProyecto tipoProyectoGuardado = this.tipoProyectoRepository.save(tipoProyecto);

            if (tipoProyectoGuardado != null) {
                tiposProyecto.add(tipoProyectoGuardado);
                respuesta.getTipoProyectoResponse().setTiposProyecto(tiposProyecto);
                respuesta.setMetadata("Respuesta exitosa", "00", "Tipo de proyecto guardado con éxito");
            } else {
                respuesta.setMetadata("Respuesta fallida", "-1", "Tipo de proyecto no guardado por malformación del request");
                return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            respuesta.setMetadata("Error en la respuesta", "-1", "Error al guardar el registro");
            e.getStackTrace();
            return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     *
     * @param tipoProyecto   objeto que se actualizara en la base de datos
     * @param idTipoProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    @Override
    public ResponseEntity<TipoProyectoResponseRest> update(TipoProyecto tipoProyecto, Long idTipoProyecto) {

        TipoProyectoResponseRest respuesta = new TipoProyectoResponseRest();
        List<TipoProyecto> tiposProyecto = new ArrayList<>();

        try {
            Optional<TipoProyecto> tipoProyectoActualizado = this.tipoProyectoRepository.findById(idTipoProyecto);

            if (tipoProyectoActualizado.isPresent()) {
                // Actualizacion del registro
                tipoProyectoActualizado.get().setNombre(tipoProyecto.getNombre());
                tipoProyectoActualizado.get().setDescripcion(tipoProyecto.getDescripcion());

                TipoProyecto tipoProyectoAActualizar = this.tipoProyectoRepository.save(tipoProyectoActualizado.get());

                if (tipoProyectoAActualizar != null) {
                    tiposProyecto.add(tipoProyectoAActualizar);

                    respuesta.getTipoProyectoResponse().setTiposProyecto(tiposProyecto);
                    respuesta.setMetadata("Respuesta exitosa", "00", "Tipo de proyecto actualizado con éxito");
                } else {
                    respuesta.setMetadata("Respuesta fallida","-1","Tipo de proyecto no actualizado por malformación del request");
                    return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.BAD_REQUEST);
                }

            } else {
                respuesta.setMetadata("Respuesta fallida", "-1", "Tipo de proyecto no encontrado para actualizar");
                return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            respuesta.setMetadata("Error en la respuesta", "-1", "Error al actualizar el registro");
            e.getStackTrace();
            return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.OK);
    }

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     *
     * @param idTipoProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo TipoProyectoResponseRest
     */
    @Override
    public ResponseEntity<TipoProyectoResponseRest> deleteById(Long idTipoProyecto) {

        TipoProyectoResponseRest respuesta = new TipoProyectoResponseRest();

        try {

            Optional<TipoProyecto> tipoProyecto = this.tipoProyectoRepository.findById(idTipoProyecto);

            if (tipoProyecto.isPresent()) {
                this.tipoProyectoRepository.deleteById(idTipoProyecto);
                respuesta.setMetadata("Respues exitosa", "00", "Se ha eliminado el registro con id " + idTipoProyecto + " exitosamente");
            } else {
                respuesta.setMetadata("Respuesta fallida", "-1", "Tipo de proyecto no encontrado");
                return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            respuesta.setMetadata("Error en la respuesta", "-1", "Error al eliminar el registro");
            e.getStackTrace();
            return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<TipoProyectoResponseRest>(respuesta, HttpStatus.OK);
    }
}
