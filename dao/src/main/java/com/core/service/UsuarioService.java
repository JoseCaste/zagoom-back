package com.core.service;

import com.core.dto.EmployeeDTO;
import com.core.dto.LoginDTO;
import com.core.dto.UserDTO;
import com.core.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> getEmployeesList();

    Object saveEmployee(EmployeeDTO employeeDTO);

    Usuario doLogin(LoginDTO loginDTO) throws Exception;

    Usuario saveUser(UserDTO userDTO);
}
