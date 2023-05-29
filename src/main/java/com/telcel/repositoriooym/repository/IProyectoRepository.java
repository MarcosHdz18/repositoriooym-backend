package com.telcel.repositoriooym.repository;

import com.telcel.repositoriooym.entity.Proyecto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author marcos.hernandez
 */
public interface IProyectoRepository extends CrudRepository<Proyecto, Long> {

    /**
     * Metodo que realiza una consulta SPEL para buscar un objeto por su atributo nombre
     * @param nombre parametro pasado por argumento para buscar el objeto que coincida con la busqueda
     * @return List<Proyecto> de objetos de tipo Proyecto
     */
    @Query("SELECT p FROM Proyecto p WHERE p.nombre like %?1%")
    List<Proyecto> findByNombreLike(String nombre);

    /**
     * Metodo que realiza una consulta SPEL para buscar un objeto por su atributo nombre
     * @param nombre parametro pasado por argumento para buscar el objeto que coincida con la busqueda
     * @return List<Proyecto> de objetos de tipo Proyecto
     */
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
}
