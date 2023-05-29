package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.Proyecto;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */

@Data
public class ProyectoResponse {

    /**
     * Lista de proyectos
     */
    private List<Proyecto> proyectos;
}
