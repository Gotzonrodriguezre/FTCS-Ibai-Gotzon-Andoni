package com.example.bar.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.example.bar.entities.Albaran;
import com.example.bar.repositories.AlbaranRepository;

@RestController
@RequestMapping("/api/albaranes")
public class AlbaranController {
    private final AlbaranRepository albaranRepository;

    public AlbaranController(AlbaranRepository albaranRepository) {
        this.albaranRepository = albaranRepository;
    }

    @GetMapping
    public List<Albaran> getAll() {
        return albaranRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Albaran> getById(@PathVariable Integer id) {
        return albaranRepository.findById(id);
    }

    @PostMapping
    public Albaran create(@RequestBody Albaran albaran) {
        return albaranRepository.save(albaran);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        albaranRepository.deleteById(id);
    }
}
