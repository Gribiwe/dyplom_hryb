package com.gribiwe.ua.web.rest;

import com.gribiwe.ua.domain.Employee;
import com.gribiwe.ua.domain.User;
import com.gribiwe.ua.service.CustomAuthorityService;
import com.gribiwe.ua.service.EmployeeService;
import com.gribiwe.ua.service.UserService;
import com.gribiwe.ua.service.dto.CustomAuthorityDTO;
import com.gribiwe.ua.service.dto.UserDTO;
import com.gribiwe.ua.service.mapper.EmployeeMapper;
import com.gribiwe.ua.service.mapper.UserMapper;
import com.gribiwe.ua.web.rest.errors.BadRequestAlertException;
import com.gribiwe.ua.service.dto.EmployeeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing {@link com.gribiwe.ua.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CustomAuthorityService customAuthorityService;


    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employeeDTO the employeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDTO, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);
        if (employeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setActivated(true);
        userDTO.setFirstName(employeeDTO.getFirstName());
        userDTO.setLastName(employeeDTO.getLastName());
        userDTO.setEmail(employeeDTO.getEmail());
        userDTO.setLogin(employeeDTO.getEmail());

        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_USER");
        userDTO.setAuthorities(authorities);

        User user = userService.createUser(userDTO);

        employeeDTO.setJhiUser(user.getId());

        EmployeeDTO result = employeeService.save(employeeDTO);

        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/employees/{id}/add-role")
    public ResponseEntity<EmployeeDTO> createEmployee(@PathVariable Long id, @RequestBody Long customAuthorityId) throws URISyntaxException {

        Optional<EmployeeDTO> one = employeeService.findOne(id);
        EmployeeDTO result = new EmployeeDTO();

        if (one.isPresent()) {
            EmployeeDTO employeeDTO = one.get();
            List<CustomAuthorityDTO> authorities = employeeDTO.getCustomAuthorities();

            boolean isPresent = false;

            if (authorities == null) {
                authorities = new ArrayList<>();
            }
                for (CustomAuthorityDTO authority : authorities) {
                    if (authority.getId() == customAuthorityId) {
                        isPresent = true;
                        break;
                    }
                }



            Optional<CustomAuthorityDTO> one1 = customAuthorityService.findOne(customAuthorityId);
            if (!isPresent) authorities.add(one1.get());

            employeeDTO.setCustomAuthorities(authorities);

            result = employeeService.save(employeeDTO);
        }

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /employees} : Updates an existing employee.
     *
     * @param employeeDTO the employeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employeeDTO);
        if (employeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeDTO result = employeeService.save(employeeDTO);

        Optional<User> byId = userService.findById(employeeDTO.getJhiUser());
        byId.ifPresent(user -> {
            user.setFirstName(employeeDTO.getFirstName());
            user.setLastName(employeeDTO.getLastName());
            user.setEmail(employeeDTO.getEmail());
            user.setLogin(employeeDTO.getEmail());

            userService.updateUser(userMapper.userToUserDTO(user));
        });

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        log.debug("REST request to get a page of Employees");
        Page<EmployeeDTO> page = employeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/by-login/{login}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String login) {
        log.debug("REST request to get Employee : {}", login);
        Optional<User> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(login);

        Optional<EmployeeDTO> employeeDTO = Optional.empty();
        if (userWithAuthoritiesByLogin.isPresent()) {
            employeeDTO = Optional.of(employeeService.findByJhiId(userWithAuthoritiesByLogin.get().getId()));
        }

        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
