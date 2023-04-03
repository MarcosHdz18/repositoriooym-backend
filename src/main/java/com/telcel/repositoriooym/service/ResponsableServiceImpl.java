package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.entity.Responsable;
import com.telcel.repositoriooym.repository.IAreaRepository;
import com.telcel.repositoriooym.repository.IResponsableRepository;
import com.telcel.repositoriooym.response.ResponsableResponseRest;
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
 *
 */

@Service
public class ResponsableServiceImpl implements IResponsableService {

    /**
     * Objeto que interactua con el CRUD respectivo
     */
    @Autowired
    private IResponsableRepository responsableRepository;

    /**
     * Objeto que interactua con el CRUD respectivo
     */
    @Autowired
    private IAreaRepository areaRepository;

    /**
     * Metodo que realiza la busqueda de todos los responsables
     * @return ResponseEntity con el estatus del response
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ResponsableResponseRest> findAll() {

        ResponsableResponseRest response = new ResponsableResponseRest();

        try {
            List<Responsable> responsables = (List<Responsable>) this.responsableRepository.findAll();

            response.getResponsableResponse().setResponsables(responsables);
            response.setMetadata("Respuesta exitosa", "00", "Responsables encontrados con éxito");
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar los registros");
            return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que se utiliza para buscar un registro por su identificador unico
     * @param idResponsable identificador unico por el que se realizara la busqueda
     * @return ResponseEntity con el estatus del response
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ResponsableResponseRest> findById(Long idResponsable) {

        ResponsableResponseRest response = new ResponsableResponseRest();
        List<Responsable> responsables = new ArrayList<>();

        try {

            Optional<Responsable> responsable = this.responsableRepository.findById(idResponsable);

            if (responsable.isPresent()) {
                responsables.add(responsable.get());
                response.getResponsableResponse().setResponsables(responsables);
                response.setMetadata("Respuesta exitosa", "00", "Responsable encontrado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Responsable no encontrado");
                return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta fallida", "-1", "Error al consultar el registro");
            e.getStackTrace();
            return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que se utilizará para persistir un objeto en la base de datos
     * @param responsable objeto que se guardara en la base de datos
     * @return ResponseEntity con el estatus del response
     */
    @Override
    @Transactional
    public ResponseEntity<ResponsableResponseRest> save(Responsable responsable, Long areaId) {

        ResponsableResponseRest response = new ResponsableResponseRest();
        List<Responsable> responsables = new ArrayList<>();

        try {
            // Buscar el area a setear en el responsable
            Optional<Area> area = this.areaRepository.findById(areaId);

            if (area.isPresent()) {
                responsable.setArea(area.get());
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada asociada al responsable");
                return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Guardado del responsable
            Responsable responsableSaved = this.responsableRepository.save(responsable);

            if (responsableSaved != null) {
                responsables.add(responsableSaved);
                response.getResponsableResponse().setResponsables(responsables);
                response.setMetadata("Respuesta exitosa", "00", "Responsable guardado con éxito");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Responsable no guardado");
                return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al guardar el responsable");
            return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que se utilizara para actualizar un registro en la base de datos
     * @param responsable objeto que se actualizara en la base de datos
     * @param idResponsable identificador unico para actualizar el objeto
     * @return ResponseEntity con el estatus del response
     */
    @Override
    @Transactional
    public ResponseEntity<ResponsableResponseRest> update(Responsable responsable, Long areaId, Long idResponsable) {

        ResponsableResponseRest response = new ResponsableResponseRest();
        List<Responsable> responsables = new ArrayList<>();

        try {
            // Buscar el area a setear en el responsable
            Optional<Area> area = this.areaRepository.findById(areaId);

            if (area.isPresent()) {
                responsable.setArea(area.get());
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Área no encontrada asociada al responsable");
                return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Busqueda del responsable a actualizar
            Optional<Responsable> responsableToUpdated = this.responsableRepository.findById(idResponsable);

            if (responsableToUpdated.isPresent()) {

                // Actualizacion del responsable
                responsableToUpdated.get().setNombre(responsable.getNombre());
                responsableToUpdated.get().setApellidoPaterno(responsable.getApellidoPaterno());
                responsableToUpdated.get().setApellidoMaterno(responsable.getApellidoMaterno());
                responsableToUpdated.get().setNumeroEmpleado(responsable.getNumeroEmpleado());
                responsableToUpdated.get().setArea(responsable.getArea());

                Responsable responsableUpdated = this.responsableRepository.save(responsableToUpdated.get());

                // Guardado del objeto en la base de datos
                if (responsableUpdated != null) {
                    responsables.add(responsableUpdated);
                    response.getResponsableResponse().setResponsables(responsables);
                    response.setMetadata("Respuesta exitosa", "00", "Responsable actualizado con éxito");
                } else {
                    response.setMetadata("Respuesta fallida", "-1", "Error en la solicitud de actualización");
                    return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Responsable no encontrado");
                return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al actualizar el responsable");
            return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Metodo que elimina un registro de la base de datos por su identificador unico
     * @param idResponsableProyecto identificador unico que se utilizara para realizar el borrado
     * @return ResponseEntity con el estatus del response
     */
    @Override
    @Transactional
    public ResponseEntity<ResponsableResponseRest> deleteById(Long idResponsableProyecto) {

        ResponsableResponseRest response = new ResponsableResponseRest();
        Optional<Responsable> responsable = this.responsableRepository.findById(idResponsableProyecto);

        try {

            if (responsable.isPresent()) {
                // Eliminamos el responsable por su identificador unico
                this.responsableRepository.deleteById(idResponsableProyecto);
                response.setMetadata("Respuesta exitosa", "00", "Responsable eliminado exitosamente");
            } else {
                response.setMetadata("Respuesta fallida", "-1", "Responsable no encontrado");
                return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al tratar de eliminar el responsable");
            return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponsableResponseRest>(response, HttpStatus.OK);
    }
}
