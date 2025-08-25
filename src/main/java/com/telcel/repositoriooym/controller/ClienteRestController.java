package com.telcel.repositoriooym.controller;

import com.telcel.repositoriooym.entity.Area;
import com.telcel.repositoriooym.entity.Cliente;
import com.telcel.repositoriooym.response.AreaResponseRest;
import com.telcel.repositoriooym.response.ClienteResponseRest;
import com.telcel.repositoriooym.service.IAreaService;
import com.telcel.repositoriooym.service.IClienteService;
import com.telcel.repositoriooym.utils.AreaExcelExporter;
import com.telcel.repositoriooym.utils.ClienteExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author marcos.hernandez
 */
@RestController
@RequestMapping("/api/v1")
public class ClienteRestController {

    /**
     * Objeto en el contexto de spring para obtener instancias
     */
    @Autowired
    private IClienteService clienteService;

    /**
     * Metodo que obtendra todos los objetos o registros de la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/clientes")
    public ResponseEntity<ClienteResponseRest> listClientes() {
        return this.clienteService.findAll();
    }

    /**
     * Metodo que obtendra un registro de la base de datos por su identificador unico
     * @param idCliente identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @GetMapping("/clientes/{idCliente}")
    public ResponseEntity<ClienteResponseRest> listClientesById(@PathVariable Long idCliente) {
        return this.clienteService.findById(idCliente);
    }

    /**
     * Metodo que guarda un objeto en la base de datos
     * @param cliente Objeto a guardar en la base de datos
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PostMapping("/clientes")
    public ResponseEntity<ClienteResponseRest> saveCliente(@RequestBody Cliente cliente) {
        return this.clienteService.save(cliente);
    }

    /**
     * Metodo que realiza la actualizacion de un objeto por su identificador unico
     * @param cliente Objeto que se actualizara
     * @param idCliente identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @PutMapping("/clientes/{idCliente}")
    public ResponseEntity<ClienteResponseRest> updateCliente(@RequestBody Cliente cliente, @PathVariable Long idCliente) {
        return this.clienteService.update(cliente, idCliente);
    }

    /**
     * Metodo que realiza el borrado de algún registro por su identificador unico
     * @param idCliente identificador unico
     * @return ResponseEntity con el estatus de la respuesta
     */
    @DeleteMapping("/clientes/{idCliente}")
    public ResponseEntity<ClienteResponseRest> deleteClienteById(@PathVariable Long idCliente) {
        this.clienteService.deleteById(idCliente);
        return ResponseEntity.noContent().build(); // Estatus 204 si salio OK
    }

    /**
     * Metodo que realizara la exportacion a excel
     * @param response Objeto de tipo HttpServletResponse
     * @throws IOException Excepcion que se lanzara cuando exista algun error de exportacion
     */
    @GetMapping("/clientes/export/excel")
    public void exportDataExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=datos_clientes";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<ClienteResponseRest> clientes = this.clienteService.findAll();

        ClienteExcelExporter fileExcelExporter = new ClienteExcelExporter(Objects.requireNonNull(clientes.getBody()).getClienteResponse().getClientes());
        fileExcelExporter.exportData(response);
    }
}
