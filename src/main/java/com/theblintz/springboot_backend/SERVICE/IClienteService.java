package com.theblintz.springboot_backend.SERVICE;

import com.theblintz.springboot_backend.MODELS.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> findAll();
    public Cliente findById(Long id);
    public Cliente save(Cliente cliente);
    public void delete(Long id);

}
