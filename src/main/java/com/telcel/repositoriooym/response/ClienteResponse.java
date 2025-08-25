package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.Cliente;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */
@Data
public class ClienteResponse {

    /**
     * Lista de clientes
     */
    private List<Cliente> clientes;
}
