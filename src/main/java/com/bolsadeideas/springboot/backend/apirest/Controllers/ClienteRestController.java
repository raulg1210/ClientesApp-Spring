package com.bolsadeideas.springboot.backend.apirest.Controllers;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //devolvemos un response entity de tipo generico por si devuelve un cliente, varios mensajes
    //de error u otros datos
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Cliente cliente = null;

        //creamos un map que devuelve string y objetos
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //devolvemos el objeto cliente y un status 200 si sale bien
        if(cliente == null){
            //si no existe el cliente cambiamos la respuesta creada
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    //el requestbody se pone porque viene en un json y asi podemos transformar los datos
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente){
        Cliente clienteNew = null;
        cliente.setCreateAt(new Date());

        //creamos un map que devuelve string y objetos
        Map<String, Object> response = new HashMap<>();

        try {
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("éxito", "El cliente ha sido creado con éxito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
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
