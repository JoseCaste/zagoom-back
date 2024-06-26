package com.core.controller;

import com.core.dto.LoginDTO;
import com.core.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/test-api")
    public ResponseEntity<?> get() throws Exception {
        return ResponseEntity.ok().body("HI WORLD!!");
    }

    @PostMapping
    public ResponseEntity<?> doLogin(@RequestBody LoginDTO loginDTO) throws Exception {
        return ResponseEntity.ok().body(usuarioService.doLogin(loginDTO));
    }
}
