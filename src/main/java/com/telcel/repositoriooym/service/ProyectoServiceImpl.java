package com.telcel.repositoriooym.service;

import com.telcel.repositoriooym.entity.Proyecto;
import com.telcel.repositoriooym.repository.IProyectoRepository;
import com.telcel.repositoriooym.repository.IResponsableRepository;
import com.telcel.repositoriooym.response.ProyectoResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcos.hernandez
 */

@Service
public class ProyectoServiceImpl implements IProyectoService {

    /**
     * Objeto de tipo IProyectoRepository con el CRUD respectivo
     */
    @Autowired
    private IProyectoRepository proyectoRepository;

    /**
     * Objeto de tipo IResponsableRepository con el CRUD respectivo
     */
    @Autowired
    private IResponsableRepository responsableRepository;

    /**
     * Metodo que lista todos los objetos de tipo Proyecto obtenidos de la base de datos
     * @return
     */
    @Override
    public ResponseEntity<ProyectoResponseRest> findAll() {

        ProyectoResponseRest response = new ProyectoResponseRest();
        List<Proyecto> proyectos = new ArrayList<>();
        List<Proyecto> listaProyectos = new ArrayList<>();

        try {
            listaProyectos = (List<Proyecto>) this.proyectoRepository.findAll();

            if (listaProyectos.size() > 0) {
                listaProyectos.stream().forEach((proyecto) -> {

                });
            }
        } catch (Exception ex) {

        }

        return null;
    }

    /**
     * @param idProyecto identificador unico del proyecto a buscar
     * @return
     */
    @Override
    public ResponseEntity<ProyectoResponseRest> findById(Long idProyecto) {
        return null;
    }

    /**
     * @param nombre String paramentro del nombre del proyecto a buscar
     * @return
     */
    @Override
    public ResponseEntity<ProyectoResponseRest> findByNombre(String nombre) {
        return null;
    }

    /**
     * @param proyecto      objeto de tipo Proyecto que sera persistido
     * @param responsableId identificador unico para persistir el responsable del proyecto
     * @return
     */
    @Override
    public ResponseEntity<ProyectoResponseRest> save(Proyecto proyecto, Long responsableId) {
        return null;
    }

    /**
     * @param proyecto      objeto de tipo Proyecto que sera el actualizado mediante su identificador unico
     * @param responsableId objeto de tipo ResponsableProyecto que se actualizara mediante su identificador unico
     * @param idProyecto    identificador unico del proyecto del objeto a actualizar
     * @return
     */
    @Override
    public ResponseEntity<ProyectoResponseRest> update(Proyecto proyecto, Long responsableId, Long idProyecto) {
        return null;
    }
}
