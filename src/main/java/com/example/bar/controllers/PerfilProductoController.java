package com.example.bar.controllers;

import com.example.bar.entities.PerfilProducto;
import com.example.bar.entities.PerfilProductoId;
import com.example.bar.repositories.PerfilProductoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfil-producto")
public class PerfilProductoController {

    private final PerfilProductoRepository repository;

    public PerfilProductoController(PerfilProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PerfilProducto> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{perfilId}/{productoId}")
    public Optional<PerfilProducto> getById(@PathVariable Integer perfilId, @PathVariable Integer productoId) {
        return repository.findById(new PerfilProductoId(perfilId, productoId));
    }

    @PostMapping
    public PerfilProducto create(@RequestBody PerfilProducto perfilProducto) {
        return repository.save(perfilProducto);
    }

    @PutMapping
    public PerfilProducto update(@RequestBody PerfilProducto perfilProducto) {
        return repository.save(perfilProducto);
    }

    @DeleteMapping("/{perfilId}/{productoId}")
    public void delete(@PathVariable Integer perfilId, @PathVariable Integer productoId) {
        repository.deleteById(new PerfilProductoId(perfilId, productoId));
    }
}
