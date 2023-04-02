package com.telcel.repositoriooym.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author marcos.hernandez
 */

@Getter
@Setter
public class ResponsableProyectoResponseRest extends ResponseRest {

    /**
     * Objeto con la respuesta del metadata
     */
    private ResponsableProyectoResponse responsableProyectoResponse = new ResponsableProyectoResponse();
}
