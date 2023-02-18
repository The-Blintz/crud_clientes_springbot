package com.theblintz.springboot_backend.controller;

import com.theblintz.springboot_backend.MODELS.Cliente;
import com.theblintz.springboot_backend.SERVICE.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> Show(@PathVariable Long id){
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);
        }catch (DataAccessException e){
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null){
            response.put("mensaje", "El Cliente ID: ".concat(id.toString().concat(" NO EXSISTE...!!! ")));
            return new ResponseEntity<Map>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente fue CREADO");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteActual = clienteService.findById(id);
        Map<String, Object> response = new HashMap<>();
        Cliente clienteUpdated = null;

        if (clienteActual == null){
            response.put("mensaje", "Error no se pudo editar, el Cliente ID: ".concat(id.toString().concat(" NO EXSISTE...!!! ")));
            return new ResponseEntity<Map>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());

            clienteUpdated  = clienteService.save(clienteActual);
        }catch (DataAccessException e){
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente fue ACTULIZADO");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<Map>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Usuario elimimnado correctamente");
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
