package com.core.repository;


import com.core.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoAndPassword(String correo, String password);

    Optional<Usuario> findByCorreo(String correo) throws Exception;
    @Query("SELECT u FROM Usuario u WHERE u.correo = ?1")
    List<Usuario> findByCorreoList(String correo) throws Exception;
}
