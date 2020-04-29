package com.gribiwe.ua.repository;

import com.gribiwe.ua.domain.Employee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    void deleteByJhiUser(Long jhiUser);

    Employee findByJhiUser(Long jhiUser);
}
