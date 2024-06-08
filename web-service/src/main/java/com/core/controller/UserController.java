package com.core.controller;

import com.core.dto.RecoveryPasswordDTO;
import com.core.dto.UserDTO;
import com.core.entity.Usuario;
import com.core.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UsuarioService usuarioSerice;

    @Autowired
    public UserController(UsuarioService usuarioService) {
        this.usuarioSerice = usuarioService;
    }
    @PostMapping
    public ResponseEntity<Usuario> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(this.usuarioSerice.saveUser(userDTO));
    }

    @PostMapping("/recovery-password")
    public ResponseEntity<?> sendRecoveryEmail(@RequestBody @Valid RecoveryPasswordDTO recoveryPasswordDTO) throws Exception {
        return ResponseEntity.ok(this.usuarioSerice.recoveryPassowrd(recoveryPasswordDTO));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody RecoveryPasswordDTO recoveryPasswordDTO) throws Exception {
        return ResponseEntity.ok(this.usuarioSerice.validateToken(recoveryPasswordDTO));
    }
}
