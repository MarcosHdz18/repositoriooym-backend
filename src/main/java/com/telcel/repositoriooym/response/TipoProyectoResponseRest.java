package com.telcel.repositoriooym.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author marcos.hernandez
 */
@Getter
@Setter
public class TipoProyectoResponseRest extends ResponseRest {

    /**
     * Objeto con la respuesta del metadata
     *
     */
    private TipoProyectoResponse tipoProyectoResponse = new TipoProyectoResponse();

}
