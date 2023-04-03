package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.repository.IAreaRepository;
import com.telcel.repositoriooym.response.AreaResponseRest;
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
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaRepository areaRepository;

    /**
     * Metodo que realiza la busqueda de todos los objetos de tipo Area
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AreaResponseRest> findAll() {

        AreaResponseRest response = new AreaResponseRest();

        try {
            List<Area> areas = (List<Area>) this.areaRepository.findAll();

            response.getAreaResponse().setAreas(areas);
            response.setMetadata("Respuesta exitosa", "00", "Lista de áreas de OyM Recepción");

        } catch (Exception e) {
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            e.getStackTrace();
            return new ResponseEntity<AreaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda de un registro por su identificador unico
     * @param idArea identificador unico por el que se realizara la busqueda
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<AreaResponseRest> findById(Long idArea) {

        AreaResponseRest response = new AreaResponseRest();
        List<Area> areas = new ArrayList<>();

        try {
            Optional<Area> area = this.areaRepository.findById(idArea);

            if (area.isPresent()) {
                areas.add(area.get());
                response.getAreaResponse().setAreas(areas);
                response.setMetadata("Respuesta exitosa", "00", "Área encontrada con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada");
                return new ResponseEntity<AreaResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al consultar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia en la base de datos
     * @param area objeto que se guardara en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional
    public ResponseEntity<AreaResponseRest> save(Area area) {

        AreaResponseRest response = new AreaResponseRest();
        List<Area> areas = new ArrayList<>();

        try {
            Area areaSaved = this.areaRepository.save(area);

            if (areaSaved != null) {
                areas.add(areaSaved);
                response.getAreaResponse().setAreas(areas);
                response.setMetadata("Respuesta exitosa", "00", "Área guardada con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no guardada por malformación del request");
                return new ResponseEntity<AreaResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al guardar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo para actualizar un registro en la base de datos por su identificador unico
     * @param area objeto que se actualizara en la base de datos
     * @param idArea identificador unico para actualizar el objeto
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional
    public ResponseEntity<AreaResponseRest> update(Area area, Long idArea) {

        AreaResponseRest response = new AreaResponseRest();
        List<Area> areas = new ArrayList<>();

        try {
            Optional<Area> areaUpdated = this.areaRepository.findById(idArea);

            if (areaUpdated.isPresent()) {
                // Actualizacion del registro
                areaUpdated.get().setNombre(area.getNombre());
                areaUpdated.get().setDescripcion(area.getDescripcion());

                Area areaToUpdate = this.areaRepository.save(areaUpdated.get());

                if (areaToUpdate != null) {
                    areas.add(areaToUpdate);

                    response.getAreaResponse().setAreas(areas);
                    response.setMetadata("Respuesta exitosa", "00", "Área actualizada con éxito");
                } else {
                    response.setMetadata("Respuesta fallida","-1","Área no actualizada por malformación del request");
                    return new ResponseEntity<AreaResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada para actualizar");
                return new ResponseEntity<AreaResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al actualizar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que permite realizar la eliminacion de un registro por su identificador unico
     * @param idArea identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity con el estatus de la respuesta
     */
    @Override
    @Transactional
    public ResponseEntity<AreaResponseRest> deleteById(Long idArea) {

        AreaResponseRest response = new AreaResponseRest();

        try {

            Optional<Area> area = this.areaRepository.findById(idArea);

            if (area.isPresent()) {
                this.areaRepository.deleteById(idArea);
                response.setMetadata("Respues exitosa", "00", "Se ha eliminado el registro con id " + idArea + " exitosamente");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada");
                return new ResponseEntity<AreaResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al eliminar el registro");
            e.getStackTrace();
            return new ResponseEntity<AreaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AreaResponseRest>(response, HttpStatus.OK);
    }
}
