package com.example.bar.repositories;

import com.example.bar.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT a.producto FROM Albaran a WHERE a.perfil.id = :perfilId GROUP BY a.producto ORDER BY COUNT(a.producto.id) DESC")
    List<Producto> findProductosMasUsadosPorPerfil(@Param("perfilId") Integer perfilId);
}
