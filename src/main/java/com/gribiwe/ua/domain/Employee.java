package com.gribiwe.ua.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;
import java.time.Instant;

/**
 * The Employee entity.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "jhiUser")
    private Long jhiUser;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "jhi_user_custom_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "custom_authority_id", referencedColumnName = "id")})

    @BatchSize(size = 20)
    protected List<CustomAuthority> customAuthorities = new ArrayList<>();

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<CustomAuthority> getCustomAuthorities() {
        return customAuthorities;
    }

    public void setCustomAuthorities(List<CustomAuthority> customAuthorities) {
        this.customAuthorities = customAuthorities;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public Employee hireDate(Instant hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return salary;
    }

    public Employee salary(Long salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public Employee department(Company department) {
        this.company = department;
        return this;
    }

    public void setDepartment(Company department) {
        this.company = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            "}";
    }
}
