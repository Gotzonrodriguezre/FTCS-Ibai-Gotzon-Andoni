package com.example.bar.repositories;

import com.example.bar.entities.PerfilProducto;
import com.example.bar.entities.PerfilProductoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilProductoRepository extends JpaRepository<PerfilProducto, PerfilProductoId> {
}
