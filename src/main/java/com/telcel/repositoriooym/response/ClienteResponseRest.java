package com.telcel.repositoriooym.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author marcos.hernandez
 */

@Setter
@Getter
public class ClienteResponseRest extends ResponseRest {

    /**
     * Objeto con la respuesta del metadata
     */
    private ClienteResponse clienteResponse = new ClienteResponse();
}
