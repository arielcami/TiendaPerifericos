package com.pe.tienda_perifericos.repository;

import com.pe.tienda_perifericos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Método para buscar productos por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
