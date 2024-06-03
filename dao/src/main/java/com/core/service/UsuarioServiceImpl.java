package com.core.service;

import com.core.dto.EmployeeDTO;
import com.core.dto.LoginDTO;
import com.core.dto.UserDTO;
import com.core.entity.Usuario;
import com.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Usuario usuario = this.usuarioRepository.findByCorreoAndPassword(loginDTO.getCorreo(), loginDTO.getPassword()).orElseThrow(()-> new Exception("Credenciales no identificadas"));
        return usuario;
    }

    @Override
    public Usuario saveUser(UserDTO userDTO) throws Exception {
        List<Usuario> byCorreoList = this.usuarioRepository.findByCorreoList(userDTO.getCorreo());

        if(!byCorreoList.isEmpty()) {
            throw new Exception("El correo ya ha sido registrado");
        }

        Usuario usuario = Usuario.builder()
                .correo(userDTO.getCorreo())
                .nombre(userDTO.getNombre())
                .apellidoPaterno(userDTO.getApellidoPaterno())
                .apellidoMaterno(userDTO.getApellidoMaterno())
                .telefono(userDTO.getTelefono())
                .password(userDTO.getPassword())
                .build();
        this.usuarioRepository.save(usuario);
        return usuario;
    }
}
