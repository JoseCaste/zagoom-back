package com.core.controller;

import com.core.dto.EmployeeDTO;
import com.core.dto.LoginDTO;
import com.core.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class EmployeeController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<?> get() throws Exception {
        return ResponseEntity.ok().body("HI WORLD!!");
    }

    @PostMapping("/")
    public ResponseEntity<?> doLogin(@RequestBody LoginDTO loginDTO) throws Exception {
        return ResponseEntity.ok().body(usuarioService.doLogin(loginDTO));
    }

    @PostMapping("/new-employee")
    public ResponseEntity<?> saveNewEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveEmployee(employeeDTO));
    }
}
