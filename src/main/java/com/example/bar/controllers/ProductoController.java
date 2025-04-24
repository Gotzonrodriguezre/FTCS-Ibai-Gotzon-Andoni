package com.example.bar.controllers;

import com.example.bar.entities.Producto;
import com.example.bar.repositories.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Producto> getById(@PathVariable Integer id) {
        return productoRepository.findById(id);
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productoRepository.deleteById(id);
    }
    
    @GetMapping("/mas-usados/{perfilId}")
    public List<Producto> getMasUsados(@PathVariable Integer perfilId) {
        return productoRepository.findProductosMasUsadosPorPerfil(perfilId);
    }
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            Producto productoActualizado = productoExistente.get();
            productoActualizado.setNombre(producto.getNombre());
            productoActualizado.setCantidad(producto.getCantidad());
            productoActualizado.setCantidadMinima(producto.getCantidadMinima());
            productoActualizado.setEstado(producto.getEstado());
            productoActualizado.setFoto(producto.getFoto());
            productoActualizado.setFechaInteraccion(LocalDateTime.now());
            return productoRepository.save(productoActualizado);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
    }


    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        producto.setId(null); // evitar que venga un ID
        Producto nuevo = productoRepository.save(producto);
        return ResponseEntity.ok(nuevo);
    }


}
