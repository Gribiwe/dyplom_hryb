package com.gribiwe.ua.service.dto;

import java.io.Serializable;

public class FullUserDto implements Serializable {

    private EmployeeDTO employeeDTO;
    private UserDTO userDTO;

    @Override
    public String toString() {
        return "CreateUserDto{" +
            "employeeDTO=" + employeeDTO +
            ", userDTO=" + userDTO +
            '}';
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
