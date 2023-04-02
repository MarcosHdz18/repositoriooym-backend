package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.AreaResponsableProyecto;
import com.telcel.repositoriooym.repository.IAreaResponsableProyecto;
import com.telcel.repositoriooym.response.AreaResponsableProyectoResponseRest;
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
public class AreaResponsableProyectoServiceImpl implements IAreaResponsableProyectoService {

    @Autowired
    private IAreaResponsableProyecto areaResponsableProyectoRepository;

    /**
     * Metodo que realiza la busqueda de todos los objetos de tipo AreaResponsableProyecto
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AreaResponsableProyectoResponseRest> findAll() {

        AreaResponsableProyectoResponseRest response = new AreaResponsableProyectoResponseRest();

        try {
            List<AreaResponsableProyecto> areas = (List<AreaResponsableProyecto>) this.areaResponsableProyectoRepository.findAll();

            response.getAreaResponsableProyectoResponse().setAreas(areas);
            response.setMetadata("Respuesta exitosa", "00", "Lista de áreas de OyM Recepción");

        } catch (Exception e) {
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            e.getStackTrace();
            return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda de un registro por su identificador unico
     * @param idAreaResponsableProyecto identificador unico por el que se realizara la busqueda
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AreaResponsableProyectoResponseRest> findById(Long idAreaResponsableProyecto) {

        AreaResponsableProyectoResponseRest response = new AreaResponsableProyectoResponseRest();
        List<AreaResponsableProyecto> areas = new ArrayList<>();

        try {
            Optional<AreaResponsableProyecto> area = this.areaResponsableProyectoRepository.findById(idAreaResponsableProyecto);

            if (area.isPresent()) {
                areas.add(area.get());
                response.getAreaResponsableProyectoResponse().setAreas(areas);
                response.setMetadata("Respuesta exitosa", "00", "Área encontrado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada");
                return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respues", "-1", "Error al consultar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia en la base de datos
     * @param areaResponsableProyecto objeto que se guardara en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional
    public ResponseEntity<AreaResponsableProyectoResponseRest> save(AreaResponsableProyecto areaResponsableProyecto) {

        AreaResponsableProyectoResponseRest response = new AreaResponsableProyectoResponseRest();
        List<AreaResponsableProyecto> areas = new ArrayList<>();

        try {
            AreaResponsableProyecto areaSaved = this.areaResponsableProyectoRepository.save(areaResponsableProyecto);

            if (areaSaved != null) {
                areas.add(areaSaved);
                response.getAreaResponsableProyectoResponse().setAreas(areas);
                response.setMetadata("Respuesta exitosa", "00", "Área guardada con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no guardada por malformación del request");
                return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al guardar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo para actualiza un registro en la base de datos por su identificador unico
     * @param areaResponsableProyecto   objeto que se actualizara en la base de datos
     * @param idAreaResponsableProyecto identificador unico para actualizar el objeto
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional
    public ResponseEntity<AreaResponsableProyectoResponseRest> update(AreaResponsableProyecto areaResponsableProyecto, Long idAreaResponsableProyecto) {

        AreaResponsableProyectoResponseRest response = new AreaResponsableProyectoResponseRest();
        List<AreaResponsableProyecto> areas = new ArrayList<>();

        try {
            Optional<AreaResponsableProyecto> areaResponsableProyectoUpdated = this.areaResponsableProyectoRepository.findById(idAreaResponsableProyecto);

            if (areaResponsableProyectoUpdated.isPresent()) {
                // Actualizacion del registro
                areaResponsableProyectoUpdated.get().setNombre(areaResponsableProyecto.getNombre());
                areaResponsableProyectoUpdated.get().setDescripcion(areaResponsableProyecto.getDescripcion());

                AreaResponsableProyecto areaResponsableProyectoToUpdate = this.areaResponsableProyectoRepository.save(areaResponsableProyectoUpdated.get());

                if (areaResponsableProyectoToUpdate != null) {
                    areas.add(areaResponsableProyectoToUpdate);

                    response.getAreaResponsableProyectoResponse().setAreas(areas);
                    response.setMetadata("Respuesta exitosa", "00", "Área actualizada con éxito");
                } else {
                    response.setMetadata("Respuesta fallida","-1","Área no actualizada por malformación del request");
                    return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada para actualizar");
                return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al actualizar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que permite realizar la eliminacion de un registro por su identificador unico
     * @param idAreaResponsableProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional
    public ResponseEntity<AreaResponsableProyectoResponseRest> deleteById(Long idAreaResponsableProyecto) {

        AreaResponsableProyectoResponseRest response = new AreaResponsableProyectoResponseRest();

        try {

            Optional<AreaResponsableProyecto> area = this.areaResponsableProyectoRepository.findById(idAreaResponsableProyecto);

            if (area.isPresent()) {
                this.areaResponsableProyectoRepository.deleteById(idAreaResponsableProyecto);
                response.setMetadata("Respues exitosa", "00", "Se ha eliminado el registro con id " + idAreaResponsableProyecto + " exitosamente");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada");
                return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al eliminar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponsableProyectoResponseRest>(response, HttpStatus.OK);
    }
}
