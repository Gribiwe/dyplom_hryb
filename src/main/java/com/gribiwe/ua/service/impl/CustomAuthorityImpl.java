package com.gribiwe.ua.service.impl;

import com.gribiwe.ua.domain.CustomAuthority;
import com.gribiwe.ua.repository.CustomAuthorityRepository;
import com.gribiwe.ua.service.CustomAuthorityService;
import com.gribiwe.ua.service.dto.CustomAuthorityDTO;
import com.gribiwe.ua.service.mapper.CustomAuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomAuthority}.
 */
@Service
@Transactional
public class CustomAuthorityImpl implements CustomAuthorityService {

    private final Logger log = LoggerFactory.getLogger(CustomAuthorityImpl.class);

    private final CustomAuthorityRepository customAuthorityRepository;

    private final CustomAuthorityMapper customAuthorityMapper;

    public CustomAuthorityImpl(CustomAuthorityRepository customAuthorityRepository, CustomAuthorityMapper customAuthorityMapper) {
        this.customAuthorityRepository = customAuthorityRepository;
        this.customAuthorityMapper = customAuthorityMapper;
    }

    /**
     * Save a customAuthority.
     *
     * @param customAuthorityDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomAuthorityDTO save(CustomAuthorityDTO customAuthorityDTO) {
        log.debug("Request to save CustomAuthority : {}", customAuthorityDTO);
        CustomAuthority customAuthority = customAuthorityMapper.toEntity(customAuthorityDTO);
        customAuthority = customAuthorityRepository.save(customAuthority);
        return customAuthorityMapper.toDto(customAuthority);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return customAuthorityRepository.findAll(pageable)
            .map(customAuthorityMapper::toDto);
    }

    /**
     * Get one customAuthority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomAuthorityDTO> findOne(Long id) {
        log.debug("Request to get CustomAuthority : {}", id);
        return customAuthorityRepository.findById(id)
            .map(customAuthorityMapper::toDto);
    }

    /**
     * Delete the customAuthority by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomAuthority : {}", id);
        customAuthorityRepository.deleteById(id);
    }
}
