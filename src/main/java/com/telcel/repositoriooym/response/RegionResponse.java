package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.Region;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */
@Data
public class RegionResponse {

    /**
     * Lista de regiones
     *
     */
    private List<Region> regiones;

}
