package com.bolsadeideas.springboot.backend.apirest.Controllers;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//al ser una api rest se añade la anotacion rest controller y request para reaalizar peticiones
//la anotacion crossorigin nos permite activar el CORS para compartir nuestros datos en destinos cruzados
//le pasamos la ruta donde se cruzan los datos e incluso podemos añadir los métodos

@CrossOrigin(origins = {"http://localhost:4200"})
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

    //va a tener el pathvariable ya que el id podria cambiar
    @GetMapping("/clientes/{id}")
    public Cliente show(@PathVariable Long id){
        return clienteService.findById(id);
    }

    //el requestbody se pone porque viene en un json y asi podemos transformar los datos
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente){
        cliente.setCreateAt(new Date());
        return clienteService.save(cliente);
    }

    //la anotacion putmapping se usa al actualizar datos
    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteActual = clienteService.findById(id);
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setEmail(cliente.getEmail());

        return clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        clienteService.delete(id);
    }
}
