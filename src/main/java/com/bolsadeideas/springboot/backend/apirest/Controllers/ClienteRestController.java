package com.bolsadeideas.springboot.backend.apirest.Controllers;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//al ser una api rest se añade la anotacion rest controller y request para reaalizar peticiones
@RequestMapping("/api")
@RestController
public class ClienteRestController {
    @Autowired
    private IClienteServices clienteService;
    //con la interfaz podemos acceder a nuestros servicios y hacer la llamada en el controller

    //con getmapping vamos poniendo la ruta donde acceder a los datos
    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }
}
