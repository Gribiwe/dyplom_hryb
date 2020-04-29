package com.gribiwe.ua.service.mapper;


import com.gribiwe.ua.domain.*;
import com.gribiwe.ua.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    @Mapping(source = "baseDepartment.id", target = "baseDepartmentId")
    CompanyDTO toDto(Company company);

    @Mapping(source = "baseDepartmentId", target = "baseDepartment")
    @Mapping(target = "departments", ignore = true)
    @Mapping(target = "removeDepartments", ignore = true)
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
