package com.gribiwe.ua.service.mapper;


import com.gribiwe.ua.domain.CustomAuthority;
import com.gribiwe.ua.domain.Document;
import com.gribiwe.ua.service.dto.CustomAuthorityDTO;
import com.gribiwe.ua.service.dto.DocumentDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomAuthorityMapper extends EntityMapper<CustomAuthorityDTO, CustomAuthority> {

    CustomAuthorityDTO toDto(CustomAuthority document);

    CustomAuthority toEntity(CustomAuthorityDTO documentDTO);

    List<CustomAuthorityDTO> toDto(List<CustomAuthority> employees);

    List<CustomAuthority> toEntity(List<CustomAuthorityDTO> employees);

    default CustomAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomAuthority document = new CustomAuthority();
        return document;
    }
}
