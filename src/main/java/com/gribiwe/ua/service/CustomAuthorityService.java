package com.gribiwe.ua.service;

import com.gribiwe.ua.service.dto.CustomAuthorityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gribiwe.ua.domain.CustomAuthority}.
 */
public interface CustomAuthorityService {

    /**
     * Save a customAuthority.
     *
     * @param customAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    CustomAuthorityDTO save(CustomAuthorityDTO customAuthorityDTO);

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomAuthorityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" customAuthority.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomAuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" customAuthority.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
