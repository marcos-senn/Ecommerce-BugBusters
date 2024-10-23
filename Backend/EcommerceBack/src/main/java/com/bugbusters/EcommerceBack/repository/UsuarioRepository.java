package com.bugbusters.EcommerceBack.repository;

import com.bugbusters.EcommerceBack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsuario(String usuario);
    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String subject);
}
