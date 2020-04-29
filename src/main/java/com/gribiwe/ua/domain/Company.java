package com.gribiwe.ua.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "comapny_name", nullable = false)
    private String comapnyName;

    @OneToOne
    @JoinColumn(unique = true)
    private Department baseDepartment;

    @OneToMany(mappedBy = "company")
    private Set<Department> departments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComapnyName() {
        return comapnyName;
    }

    public Company comapnyName(String comapnyName) {
        this.comapnyName = comapnyName;
        return this;
    }

    public void setComapnyName(String comapnyName) {
        this.comapnyName = comapnyName;
    }

    public Department getBaseDepartment() {
        return baseDepartment;
    }

    public Company baseDepartment(Department department) {
        this.baseDepartment = department;
        return this;
    }

    public void setBaseDepartment(Department department) {
        this.baseDepartment = department;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Company departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Company addDepartments(Department department) {
        this.departments.add(department);
        department.setCompany(this);
        return this;
    }

    public Company removeDepartments(Department department) {
        this.departments.remove(department);
        department.setCompany(null);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", comapnyName='" + getComapnyName() + "'" +
            "}";
    }
}
