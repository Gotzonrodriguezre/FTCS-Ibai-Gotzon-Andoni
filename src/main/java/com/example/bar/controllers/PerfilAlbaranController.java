package com.example.bar.controllers;

import com.example.bar.entities.PerfilAlbaran;
import com.example.bar.entities.PerfilAlbaranId;
import com.example.bar.repositories.PerfilAlbaranRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfil-albaran")
public class PerfilAlbaranController {

    private final PerfilAlbaranRepository repository;

    public PerfilAlbaranController(PerfilAlbaranRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PerfilAlbaran> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{perfilId}/{albaranId}")
    public Optional<PerfilAlbaran> getById(@PathVariable Integer perfilId, @PathVariable Integer albaranId) {
        return repository.findById(new PerfilAlbaranId(perfilId, albaranId));
    }

    @PostMapping
    public PerfilAlbaran create(@RequestBody PerfilAlbaran perfilAlbaran) {
        return repository.save(perfilAlbaran);
    }

    @PutMapping
    public PerfilAlbaran update(@RequestBody PerfilAlbaran perfilAlbaran) {
        return repository.save(perfilAlbaran);
    }

    @DeleteMapping("/{perfilId}/{albaranId}")
    public void delete(@PathVariable Integer perfilId, @PathVariable Integer albaranId) {
        repository.deleteById(new PerfilAlbaranId(perfilId, albaranId));
    }
}
