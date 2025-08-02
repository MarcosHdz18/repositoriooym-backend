package com.telcel.repositoriooym.service.impl;

import com.telcel.repositoriooym.entity.Region;
import com.telcel.repositoriooym.entity.Sitio;
import com.telcel.repositoriooym.repository.IRegionRepository;
import com.telcel.repositoriooym.repository.ISitioRepository;
import com.telcel.repositoriooym.response.SitioResponseRest;
import com.telcel.repositoriooym.service.ISitioService;
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
public class SitioServiceImpl implements ISitioService {

    @Autowired
    private ISitioRepository sitioRepository;

    @Autowired
    private IRegionRepository regionRepository;

    /**
     * Metodo que emite una lista de objetos del tipo ResponseEntity
     *
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<SitioResponseRest> findAll() {

        SitioResponseRest response = new SitioResponseRest();

        try {
            List<Sitio> sitios = (List<Sitio>) this.sitioRepository.findAll();

            response.getSitioResponse().setSitios(sitios);
            response.setMetadata("Respuesta exitosa", "00", "Sitios encontrados con éxito");
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            return new ResponseEntity<SitioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SitioResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la busqueda por id
     *
     * @param idSitio identificador unico por el que se realizara la busqueda
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<SitioResponseRest> findById(Long idSitio) {
        SitioResponseRest response = new SitioResponseRest();
        List<Sitio> sitios = new ArrayList<>();

        try {

            Optional<Sitio> sitio = this.sitioRepository.findById(idSitio);

            if (sitio.isPresent()) {
                sitios.add(sitio.get());
                response.getSitioResponse().setSitios(sitios);
                response.setMetadata("Respuesta exitosa", "00", "Sitio encontrado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Sitio no encontrado");
                return new ResponseEntity<SitioResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar el registro");
            e.getStackTrace();
            return new ResponseEntity<SitioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SitioResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la persistencia de un objeto en la base de datos
     *
     * @param sitio objeto que se guardara en la base de datos
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    @Override
    @Transactional
    public ResponseEntity<SitioResponseRest> save(Sitio sitio, Long regionId) {

        SitioResponseRest response = new SitioResponseRest();
        List<Sitio> sitios = new ArrayList<>();

        try {
            // Buscar el area a setear en el responsable
            Optional<Region> region = this.regionRepository.findById(regionId);

            if (region.isPresent()) {
                sitio.setRegion(region.get());
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Región no encontrada asociada al responsable");
                return new ResponseEntity<SitioResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Guardado del responsable
            Sitio sitioSaved = this.sitioRepository.save(sitio);

            if (sitioSaved != null) {
                sitios.add(sitioSaved);
                response.getSitioResponse().setSitios(sitios);
                response.setMetadata("Respuesta exitosa", "00", "Sitio guardado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Sitio no guardado");
                return new ResponseEntity<SitioResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al guardar el sitio");
            return new ResponseEntity<SitioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SitioResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto en la base de datos
     *
     * @param sitio   objeto que se actualizara en la base de datos
     * @param idSitio identificador unico para actualizar el objeto
     * @param regionId identificador unico de la region
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    @Override
    public ResponseEntity<SitioResponseRest> update(Sitio sitio, Long idSitio, Long regionId) {

        SitioResponseRest response = new SitioResponseRest();
        List<Sitio> sitios = new ArrayList<>();

        try {
            // Buscar la region a setear en el sitio
            Optional<Region> region = this.regionRepository.findById(regionId);

            if (region.isPresent()) {
                sitio.setRegion(region.get());
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Region no encontrada asociada al sitio");
                return new ResponseEntity<SitioResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Busqueda del responsable a actualizar
            Optional<Sitio> sitioToUpdated = this.sitioRepository.findById(idSitio);

            if (sitioToUpdated.isPresent()) {

                // Actualizacion del sitio
                sitioToUpdated.get().setNombre(sitio.getNombre());
                sitioToUpdated.get().setTreeChar(sitio.getTreeChar());
                sitioToUpdated.get().setDireccion(sitio.getDireccion());
                sitioToUpdated.get().setNombreContacto(sitio.getNombreContacto());
                sitioToUpdated.get().setTelefonoContacto(sitio.getTelefonoContacto());
                sitioToUpdated.get().setCorreoContacto(sitio.getCorreoContacto());
                sitioToUpdated.get().setRegion(sitio.getRegion());

                Sitio sitioUpdated = this.sitioRepository.save(sitioToUpdated.get());

                // Guardado del objeto en la base de datos
                if (sitioUpdated != null) {
                    sitios.add(sitioUpdated);
                    response.getSitioResponse().setSitios(sitios);
                    response.setMetadata("Respuesta exitosa", "00", "Sitio actualizado con éxito");
                } else {
                    response.setMetadata("Respuesta fallida", "-1", "Error en la solicitud de actualización");
                    return new ResponseEntity<SitioResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Sitio no encontrado");
                return new ResponseEntity<SitioResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al actualizar el sitio");
            return new ResponseEntity<SitioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SitioResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que se utilizara para realizar el borrado en la base de datos
     *
     * @param idSitio identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity de objetos de tipo SitioResponseRest
     */
    @Override
    public ResponseEntity<SitioResponseRest> deleteById(Long idSitio) {

        SitioResponseRest response = new SitioResponseRest();
        Optional<Sitio> sitio = this.sitioRepository.findById(idSitio);

        try {

            if (sitio.isPresent()) {
                // Eliminamos el sitio por su identificador unico
                this.sitioRepository.deleteById(idSitio);
                response.setMetadata("Respuesta exitosa", "00", "Sitio eliminado exitosamente");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Sitio no encontrado");
                return new ResponseEntity<SitioResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al tratar de eliminar el sitio");
            return new ResponseEntity<SitioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<SitioResponseRest>(response, HttpStatus.OK);
    }
}
