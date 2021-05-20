package com.gribiwe.ua.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_custom_authority")
public class CustomAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(name = "companyId")
    private Long companyId;

    @Column(name = "rank")
    private Long rank;

    @Column(name = "canUploadFiles")
    private Boolean canUploadFiles;

    @Column(name = "isManager")
    private Boolean isManager;

    @Column(name = "canCreateUser")
    private Boolean canCreateUser;

    @Column(name = "canReviewUserList")
    private Boolean canReviewUserList;

    @Column(name = "canShareFile")
    private Boolean canShareFile;

    @Column(name = "canSeeNeighbor")
    private Boolean canSeeNeighbor;

    @Column(name = "canSeeRemovedFiles")
    private Boolean canSeeRemovedFiles;

    @Column(name = "canGrantLowerRoles")
    private Boolean canGrantLowerRoles;

    @Column(name = "canManageFilesOfTheLowerRoles")
    private Boolean canManageFilesOfTheLowerRoles;

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Boolean getCanUploadFiles() {
        return canUploadFiles;
    }

    public void setCanUploadFiles(Boolean canUploadFiles) {
        this.canUploadFiles = canUploadFiles;
    }

    public Boolean getCanCreateUser() {
        return canCreateUser;
    }

    public void setCanCreateUser(Boolean canCreateUser) {
        this.canCreateUser = canCreateUser;
    }

    public Boolean getCanReviewUserList() {
        return canReviewUserList;
    }

    public void setCanReviewUserList(Boolean canReviewUserList) {
        this.canReviewUserList = canReviewUserList;
    }

    public Boolean getCanShareFile() {
        return canShareFile;
    }

    public void setCanShareFile(Boolean canShareFile) {
        this.canShareFile = canShareFile;
    }

    public Boolean getCanSeeNeighbor() {
        return canSeeNeighbor;
    }

    public void setCanSeeNeighbor(Boolean canSeeNeighbor) {
        this.canSeeNeighbor = canSeeNeighbor;
    }

    public Boolean getCanSeeRemovedFiles() {
        return canSeeRemovedFiles;
    }

    public void setCanSeeRemovedFiles(Boolean canSeeRemovedFiles) {
        this.canSeeRemovedFiles = canSeeRemovedFiles;
    }

    public Boolean getCanGrantLowerRoles() {
        return canGrantLowerRoles;
    }

    public void setCanGrantLowerRoles(Boolean canGrantLowerRoles) {
        this.canGrantLowerRoles = canGrantLowerRoles;
    }

    public Boolean getCanManageFilesOfTheLowerRoles() {
        return canManageFilesOfTheLowerRoles;
    }

    public void setCanManageFilesOfTheLowerRoles(Boolean canManageFilesOfTheLowerRoles) {
        this.canManageFilesOfTheLowerRoles = canManageFilesOfTheLowerRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomAuthority)) {
            return false;
        }
        return Objects.equals(name, ((CustomAuthority) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Authority{" +
            "name='" + name + '\'' +
            "}";
    }
}
