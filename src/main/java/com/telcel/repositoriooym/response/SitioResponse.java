package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.Sitio;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */
@Data
public class SitioResponse {

    /**
     * Lista de sitios
     */
    private List<Sitio> sitios;
}
