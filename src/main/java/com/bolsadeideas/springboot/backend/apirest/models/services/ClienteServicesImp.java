package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
//con esta anotacion Service marcamos que lo usamos como servicio
@Service
public class ClienteServicesImp implements IClienteServices{
    @Autowired
    private IClienteDao clienteDao;
    @Override
    @Transactional(readOnly = true)
    //creamos el método implementado haciendo un cast a la clase cliente
    //transformandolo en una lista y con la anotacion transactional permitimos
    //que haya transacciones. La importacion tiene que ser transaction.annotation
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }
}
