package com.multiristorante.app.backend.controllers;

import com.multiristorante.app.backend.Entities.Producto;
import com.multiristorante.app.backend.Entities.Rol;
import com.multiristorante.app.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    ProductoRepository productoRepository;

    @GetMapping("/all")
    public List<Producto> getProductoAll(){
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Producto getProductosbyId(@PathVariable Integer id) {

        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {
            return producto.get();
        }

        return null;

    }

    @PostMapping
    public Producto postProductos(@RequestBody Producto producto) {
        productoRepository.save(producto);
        return producto;
    }


    @PutMapping("/{id}")
    public Producto putProductosbyId(@PathVariable Integer id, @RequestBody Producto producto) {

        Optional<Producto> productoCurrent = productoRepository.findById(id);

        if (productoCurrent.isPresent()) {

            Producto productoReturn = productoCurrent.get();


            productoReturn.setNombre(producto.getNombre());


            productoRepository.save(productoReturn);

            return productoReturn;
        }

        return null;

    }

    @DeleteMapping("/{id}")
    public Producto deleteProductobyId(@PathVariable Integer id) {

        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {

            Producto productoReturn = producto.get();

            productoRepository.deleteById(id);

            return productoReturn;
        }

        return null;

    }
}
