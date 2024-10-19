package com.bugbusters.EcommerceBack.controller;

import com.bugbusters.EcommerceBack.dto.DatosJWTDTO;
import com.bugbusters.EcommerceBack.dto.UsuarioLoginDTO;
import com.bugbusters.EcommerceBack.entity.Usuario;
import com.bugbusters.EcommerceBack.service.TokenService;
import com.bugbusters.EcommerceBack.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iniciar-sesion")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;


    @PostMapping()
    public ResponseEntity login(@RequestBody @Valid UsuarioLoginDTO loginDTO) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(loginDTO.email());
        String token = tokenService.generarToken(usuario);
        return ResponseEntity.ok(new DatosJWTDTO(token));
    }
}

