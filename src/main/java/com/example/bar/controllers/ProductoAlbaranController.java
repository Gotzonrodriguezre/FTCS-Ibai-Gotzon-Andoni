package com.example.bar.controllers;

import com.example.bar.entities.ProductoAlbaran;
import com.example.bar.entities.ProductoAlbaranId;
import com.example.bar.repositories.ProductoAlbaranRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto-albaran")
public class ProductoAlbaranController {

    private final ProductoAlbaranRepository repository;

    public ProductoAlbaranController(ProductoAlbaranRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProductoAlbaran> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{productoId}/{albaranId}")
    public Optional<ProductoAlbaran> getById(@PathVariable Integer productoId, @PathVariable Integer albaranId) {
        return repository.findById(new ProductoAlbaranId(productoId, albaranId));
    }

    @PostMapping
    public ProductoAlbaran create(@RequestBody ProductoAlbaran productoAlbaran) {
        return repository.save(productoAlbaran);
    }

    @PutMapping
    public ProductoAlbaran update(@RequestBody ProductoAlbaran productoAlbaran) {
        return repository.save(productoAlbaran);
    }

    @DeleteMapping("/{productoId}/{albaranId}")
    public void delete(@PathVariable Integer productoId, @PathVariable Integer albaranId) {
        repository.deleteById(new ProductoAlbaranId(productoId, albaranId));
    }
}
