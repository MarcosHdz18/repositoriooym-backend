package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.AreaResponsableProyecto;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */

@Data
public class AreaResponsableProyectoResponse {

    /**
     * Lista de areas
     */
    private List<AreaResponsableProyecto> areas;
}
