package com.gribiwe.ua.service.dto;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.gribiwe.ua.domain.Employee} entity.
 */
@ApiModel(description = "The Employee entity.")
public class EmployeeDTO implements Serializable {

    private Long id;
    private Long jhiUser;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Instant hireDate;

    private Long salary;

    private Long companyId;

    private List<CustomAuthorityDTO> customAuthorities;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, Long jhiUser, String firstName, String lastName, String email, String phoneNumber, Instant hireDate, Long salary, Long companyId, List<CustomAuthorityDTO> customAuthorities) {
        this.id = id;
        this.jhiUser = jhiUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
        this.companyId = companyId;
        this.customAuthorities = customAuthorities;
    }

    public List<CustomAuthorityDTO> getCustomAuthorities() {
        return customAuthorities;
    }

    public void setCustomAuthorities(List<CustomAuthorityDTO> customAuthorities) {
        this.customAuthorities = customAuthorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJhiUser(Long jhiUser) {
        this.jhiUser = jhiUser;
    }

    public Long getJhiUser() {
        return jhiUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long departmentId) {
        this.companyId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", departmentId=" + getCompanyId() +
            "}";
    }
}
