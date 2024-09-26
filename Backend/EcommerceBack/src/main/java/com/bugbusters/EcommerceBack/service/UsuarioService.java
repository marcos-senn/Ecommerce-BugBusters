package com.bugbusters.EcommerceBack.service;

import com.bugbusters.EcommerceBack.dto.UsuarioDTO;
import com.bugbusters.EcommerceBack.entity.Usuario;
import com.bugbusters.EcommerceBack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Registro de usuarios
    public Usuario registrarUsuario(UsuarioDTO usuarioDTO) { 
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsuario()) || 
            usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El usuario o email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setRoles(new HashSet<>(Set.of("ROLE_USER")));

        return usuarioRepository.save(usuario); //se guarda al usuario
    }
    //Inicio de sesión o login
    public Usuario obtenerUsuarioPorUsuario(String usuario) {
        return usuarioRepository.findByUsername(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        // Busca al usuario por su nombre de usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(usuario);

        if (!usuarioOpt.isPresent()) { //método para verificar si el Optional contiene un valor
            throw new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + usuario);
        }

        Usuario usuarioEntidad = usuarioOpt.get();
        
        // Retorna al usuario convertido en UserDetails
        return new org.springframework.security.core.userdetails.User( //autenticación don Spring Security
                usuarioEntidad.getUsuario(),
                usuarioEntidad.getPassword(),
                true, true, true, true, 
                usuarioEntidad.getAuthorities() //roles asociados con el usuario
        );
    }
}
