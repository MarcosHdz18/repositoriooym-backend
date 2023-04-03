package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.Area;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */

@Data
public class AreaResponse {

    /**
     * Lista de areas
     */
    private List<Area> areas;
}
