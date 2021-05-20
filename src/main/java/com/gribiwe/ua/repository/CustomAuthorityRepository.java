package com.gribiwe.ua.repository;

import com.gribiwe.ua.domain.Company;
import com.gribiwe.ua.domain.CustomAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomAuthorityRepository extends JpaRepository<CustomAuthority, Long> {
}
