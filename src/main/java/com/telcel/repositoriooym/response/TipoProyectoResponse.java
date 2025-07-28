package com.telcel.repositoriooym.response;

import com.telcel.repositoriooym.entity.TipoProyecto;
import lombok.Data;

import java.util.List;

/**
 * @author marcos.hernandez
 */
@Data
public class TipoProyectoResponse {

    // Lista de los tipos de proyecto
    private List<TipoProyecto> tiposProyecto;
}
