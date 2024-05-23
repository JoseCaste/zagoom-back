package com.core.service;

import com.core.dto.EmployeeDTO;
import com.core.dto.LoginDTO;
import com.core.entity.Usuario;
import com.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getEmployeesList() {
        return usuarioRepository.findAll();
    }

    @Override
    public String saveEmployee(EmployeeDTO employeeDTO) {
        return "Usuario creado";
    }

    @Override
    public Usuario doLogin(LoginDTO loginDTO) throws Exception {
        Usuario usuario = this.usuarioRepository.findByCorreoAndPassword(loginDTO.getCorreo(), loginDTO.getPassword()).orElseThrow(()-> new Exception("No se ha identificado un registro"));
        return usuario;
    }
}
