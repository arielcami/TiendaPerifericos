package com.pe.tienda_perifericos.service;

import com.pe.tienda_perifericos.model.Producto;
import com.pe.tienda_perifericos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Método para obtener un producto por su ID
    public Producto obtenerProductoPorId(int productoId) {
        return productoRepository.findById(productoId).orElse(null);
    }

    // Método para buscar productos por nombre
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);  // Búsqueda sin importar mayúsculas o minúsculas
    }
}
