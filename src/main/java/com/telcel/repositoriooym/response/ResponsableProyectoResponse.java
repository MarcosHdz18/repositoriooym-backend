package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.ResponsableProyecto;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */

@Data
public class ResponsableProyectoResponse {

    /**
     * Lista de responsables
      */
    private List<ResponsableProyecto> responsables;

}
