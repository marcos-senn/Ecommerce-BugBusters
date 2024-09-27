package com.bugbusters.EcommerceBack.controller;

import com.bugbusters.EcommerceBack.dto.UsuarioDTO;
import com.bugbusters.EcommerceBack.entity.Usuario;
import com.bugbusters.EcommerceBack.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //TODO: Desactivar la protección de este endpoint
    @PostMapping("") //modificar ruta según el front
    public String registrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        usuarioService.registrarUsuario(usuarioDTO);
        return "Usuario registrado con éxito";//comentar esta línea y después cambiar por HTTP 
    }

    @GetMapping("/{usuario}")  //modificar ruta según el front
    public Usuario obtenerUsuario(@RequestParam String usuario) {
        return usuarioService.obtenerUsuarioPorUsuario(usuario);
    }
}
