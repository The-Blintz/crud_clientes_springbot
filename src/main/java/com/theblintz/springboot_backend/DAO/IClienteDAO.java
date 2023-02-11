package com.theblintz.springboot_backend.DAO;

import com.theblintz.springboot_backend.MODELS.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDAO extends CrudRepository<Cliente, Long> {
}
