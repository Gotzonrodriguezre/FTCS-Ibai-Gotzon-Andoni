package com.example.bar.repositories;

import com.example.bar.entities.ProductoAlbaran;
import com.example.bar.entities.ProductoAlbaranId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoAlbaranRepository extends JpaRepository<ProductoAlbaran, ProductoAlbaranId> {
}
