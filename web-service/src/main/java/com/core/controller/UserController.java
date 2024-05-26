package com.core.controller;

import com.core.dto.UserDTO;
import com.core.entity.Usuario;
import com.core.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UsuarioService usuarioSerice;

    @Autowired
    public UserController(UsuarioService usuarioService) {
        this.usuarioSerice = usuarioService;
    }
    @PostMapping
    public ResponseEntity<Usuario> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(this.usuarioSerice.saveUser(userDTO));
    }
}
