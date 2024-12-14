package com.pe.tienda_perifericos.repository;

import com.pe.tienda_perifericos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombre(String nombre);
    Usuario findByCorreo(String correo); // Método para buscar por correo si es necesario
}
