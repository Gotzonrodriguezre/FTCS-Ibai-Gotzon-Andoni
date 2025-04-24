package com.example.bar.controllers;

import com.example.bar.entities.Perfil;
import com.example.bar.repositories.PerfilRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {
    private final PerfilRepository perfilRepository;

    public PerfilController(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return perfilRepository.findAll().stream().map(perfil -> {
            Map<String, Object> perfilMap = new HashMap<>();
            perfilMap.put("id", perfil.getId());
            perfilMap.put("nombre", perfil.getNombre());
            perfilMap.put("correo", perfil.getCorreo());
            perfilMap.put("estado", perfil.getEstado());
            perfilMap.put("tipo", perfil.getTipo());

            if (perfil.getFoto() != null) {
                String base64Foto = Base64.getEncoder().encodeToString(perfil.getFoto());
                perfilMap.put("foto", base64Foto);
            }

            return perfilMap;
        }).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public Optional<Perfil> getById(@PathVariable Integer id) {
        return perfilRepository.findById(id);
    }

    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        perfilRepository.deleteById(id);
    }
}
