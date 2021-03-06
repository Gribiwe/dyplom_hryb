package com.gribiwe.ua.service.impl;

import com.gribiwe.ua.domain.CustomAuthority;
import com.gribiwe.ua.domain.User;
import com.gribiwe.ua.service.EmployeeService;
import com.gribiwe.ua.domain.Employee;
import com.gribiwe.ua.repository.EmployeeRepository;
import com.gribiwe.ua.service.UserService;
import com.gribiwe.ua.service.dto.CustomAuthorityDTO;
import com.gribiwe.ua.service.dto.EmployeeDTO;
import com.gribiwe.ua.service.mapper.CustomAuthorityMapper;
import com.gribiwe.ua.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    private final UserService userService;

    private final EmployeeMapper employeeMapper;

    @Autowired
    private CustomAuthorityMapper customAuthorityMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userService = userService;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        List<CustomAuthority> customAuthorities = customAuthorityMapper.toEntity(employeeDTO.getCustomAuthorities());
        employee.setCustomAuthorities(customAuthorities);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        Page<EmployeeDTO> map = employeeRepository.findAll(pageable)
            .map(this::mapDTO)
            .map(this::getJhiData);

        return map;
    }

    EmployeeDTO mapDTO(Employee employee) {
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        List<CustomAuthorityDTO> customAuthorityDTOS = customAuthorityMapper.toDto(employee.getCustomAuthorities());
        employeeDTO.setCustomAuthorities(customAuthorityDTOS);

        return employeeDTO;
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id)
            .map(this::mapDTO)
            .map(this::getJhiData);
    }

    private EmployeeDTO getJhiData(EmployeeDTO employee) {
        Optional<User> byId = userService.findById(employee.getJhiUser());
        if (byId.isPresent()) {
            User user = byId.get();
            employee.setFirstName(user.getFirstName());
            employee.setLastName(user.getLastName());
            employee.setEmail(user.getEmail());
        }

        return employee;
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
        userService.deleteUser(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void deleteByJhiIId(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteByJhiUser(id);
        userService.deleteUser(id);
    }

    @Override
    public EmployeeDTO findByJhiId(Long userId) {
        return this.mapDTO(employeeRepository.findByJhiUser(userId));
    }
}
