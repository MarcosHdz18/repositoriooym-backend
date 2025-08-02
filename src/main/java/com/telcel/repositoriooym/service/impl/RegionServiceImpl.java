package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.entity.Region;
import com.telcel.repositoriooym.repository.IRegionRepository;
import com.telcel.repositoriooym.response.AreaResponseRest;
import com.telcel.repositoriooym.response.RegionResponseRest;
import com.telcel.repositoriooym.service.IRegionService;
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
public class RegionServiceImpl implements IRegionService {

    @Autowired
    private IRegionRepository regionRepository;

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     *
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<RegionResponseRest> findAll() {
        RegionResponseRest response = new RegionResponseRest();

        try {
            List<Region> regiones = (List<Region>) this.regionRepository.findAll();

            response.getRegionResponse().setRegiones(regiones);
            response.setMetadata("Respuesta exitosa", "00", "Lista de regiones");

        } catch (Exception e) {
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            e.getStackTrace();
            return new ResponseEntity<RegionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RegionResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda por id
     *
     * @param idRegion identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<RegionResponseRest> findById(Long idRegion) {
        RegionResponseRest response = new RegionResponseRest();
        List<Region> regiones = new ArrayList<>();

        try {
            Optional<Region> region = this.regionRepository.findById(idRegion);
            if (region.isPresent()) {
                regiones.add(region.get());
                response.getRegionResponse().setRegiones(regiones);
                response.setMetadata("Respuesta exitosa", "00", "Región encontrada con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Región no encontrada");
                return new ResponseEntity<RegionResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al consultar el registro");
            e.getStackTrace();
            return new ResponseEntity<RegionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<RegionResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     *
     * @param region objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    @Override
    public ResponseEntity<RegionResponseRest> save(Region region) {
        RegionResponseRest response = new RegionResponseRest();
        List<Region> regiones = new ArrayList<>();

        try {
            Region regionSaved = this.regionRepository.save(region);

            if (regionSaved != null) {
                regiones.add(regionSaved);
                response.getRegionResponse().setRegiones(regiones);
                response.setMetadata("Respuesta exitosa", "00", "Región guardada con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Región no guardada por malformación del request");
                return new ResponseEntity<RegionResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al guardar el registro");
            e.getStackTrace();
            return new ResponseEntity<RegionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<RegionResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     *
     * @param region   objeto que se actualizara en la base de datos
     * @param idRegion identificador unico para actualizar el objeto
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    @Override
    public ResponseEntity<RegionResponseRest> update(Region region, Long idRegion) {
        RegionResponseRest response = new RegionResponseRest();
        List<Region> regiones = new ArrayList<>();

        try {
            Optional<Region> regionUpdated = this.regionRepository.findById(idRegion);

            if (regionUpdated.isPresent()) {
                // Actualizacion del registro
                regionUpdated.get().setNombre(region.getNombre());

                Region regionToUpdate = this.regionRepository.save(regionUpdated.get());

                if (regionToUpdate != null) {
                    regiones.add(regionToUpdate);

                    response.getRegionResponse().setRegiones(regiones);
                    response.setMetadata("Respuesta exitosa", "00", "Región actualizada con éxito");
                } else {
                    response.setMetadata("Respuesta fallida","-1","Región no actualizada por malformación del request");
                    return new ResponseEntity<RegionResponseRest>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                response.setMetadata("Respuesta fallida", "-1", "Región no encontrada para actualizar");
                return new ResponseEntity<RegionResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al actualizar el registro");
            e.getStackTrace();
            return new ResponseEntity<RegionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<RegionResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     *
     * @param idRegion identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo RegionResponseRest
     */
    @Override
    public ResponseEntity<RegionResponseRest> deleteById(Long idRegion) {

        RegionResponseRest response = new RegionResponseRest();

        try {

            Optional<Region> region = this.regionRepository.findById(idRegion);

            if (region.isPresent()) {
                this.regionRepository.deleteById(idRegion);
                response.setMetadata("Respues exitosa", "00", "Se ha eliminado el registro con id " + idRegion + " exitosamente");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Región no encontrada");
                return new ResponseEntity<RegionResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error en la respuesta", "-1", "Error al eliminar el registro");
            e.getStackTrace();
            return new ResponseEntity<RegionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<RegionResponseRest>(response, HttpStatus.OK);
    }
}
