package com.gribiwe.ua.service.mapper;


import com.gribiwe.ua.domain.*;
import com.gribiwe.ua.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, CustomAuthorityMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "company.id", target = "companyId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "companyId", target = "company")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
