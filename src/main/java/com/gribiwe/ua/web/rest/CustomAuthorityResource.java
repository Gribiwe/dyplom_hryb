package com.gribiwe.ua.web.rest;

import com.gribiwe.ua.service.CustomAuthorityService;
import com.gribiwe.ua.service.dto.CustomAuthorityDTO;
import com.gribiwe.ua.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gribiwe.ua.domain.CustomAuthority}.
 */
@RestController
@RequestMapping("/api")
public class CustomAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(CustomAuthorityResource.class);

    private static final String ENTITY_NAME = "customAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomAuthorityService customAuthorityService;

    public CustomAuthorityResource(CustomAuthorityService customAuthorityService) {
        this.customAuthorityService = customAuthorityService;
    }

    /**
     * {@code POST  /customAuthorities} : Create a new customAuthority.
     *
     * @param customAuthorityDTO the customAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customAuthorityDTO, or with status {@code 400 (Bad Request)} if the customAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customAuthorities")
    public ResponseEntity<CustomAuthorityDTO> createCustomAuthority(@Valid @RequestBody CustomAuthorityDTO customAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save CustomAuthority : {}", customAuthorityDTO);
        if (customAuthorityDTO.getId() != null) { 
            throw new BadRequestAlertException("A new customAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomAuthorityDTO result = customAuthorityService.save(customAuthorityDTO);
        return ResponseEntity.created(new URI("/api/customAuthorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customAuthorities} : Updates an existing customAuthority.
     *
     * @param customAuthorityDTO the customAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the customAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customAuthorities")
    public ResponseEntity<CustomAuthorityDTO> updateCustomAuthority(@Valid @RequestBody CustomAuthorityDTO customAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update CustomAuthority : {}", customAuthorityDTO);
        if (customAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomAuthorityDTO result = customAuthorityService.save(customAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customAuthorities} : get all the customAuthorities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customAuthorities in body.
     */
    @GetMapping("/customAuthorities")
    public ResponseEntity<List<CustomAuthorityDTO>> getAllCompanies(Pageable pageable) {
        log.debug("REST request to get a page of Companies");
        Page<CustomAuthorityDTO> page = customAuthorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customAuthorities/:id} : get the "id" customAuthority.
     *
     * @param id the id of the customAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customAuthorities/{id}")
    public ResponseEntity<CustomAuthorityDTO> getCustomAuthority(@PathVariable Long id) {
        log.debug("REST request to get CustomAuthority : {}", id);
        Optional<CustomAuthorityDTO> customAuthorityDTO = customAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customAuthorityDTO);
    }

    /**
     * {@code DELETE  /customAuthorities/:id} : delete the "id" customAuthority.
     *
     * @param id the id of the customAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customAuthorities/{id}")
    public ResponseEntity<Void> deleteCustomAuthority(@PathVariable Long id) {
        log.debug("REST request to delete CustomAuthority : {}", id);
        customAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
