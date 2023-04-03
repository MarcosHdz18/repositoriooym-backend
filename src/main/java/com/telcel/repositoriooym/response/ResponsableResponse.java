package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.Responsable;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */

@Data
public class ResponsableResponse {

    /**
     * Lista de responsables
      */
    private List<Responsable> responsables;

}
