package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Bitacora;
import com.telcel.repositoriooym.repository.IBitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BitacoraRestController {

    /**
     * Objeto para instanciar bitacoraRepository
     */
    @Autowired
    private IBitacoraRepository bitacoraRepository;

    /**
     * Metodo que devuelve la lista de las bitacoras
     * @return
     */
    @GetMapping("/bitacoras")
    public List<Bitacora> findAll() {
        return (List<Bitacora>) this.bitacoraRepository.findAll();
    }
}
