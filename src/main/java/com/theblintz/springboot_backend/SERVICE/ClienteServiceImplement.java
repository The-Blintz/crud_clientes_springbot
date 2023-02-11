package com.theblintz.springboot_backend.SERVICE;


import com.theblintz.springboot_backend.DAO.IClienteDAO;
import com.theblintz.springboot_backend.MODELS.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImplement implements IClienteService {

    @Autowired
    private IClienteDAO iClienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) iClienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return iClienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return iClienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        iClienteDao.deleteById(id);
    }
}
