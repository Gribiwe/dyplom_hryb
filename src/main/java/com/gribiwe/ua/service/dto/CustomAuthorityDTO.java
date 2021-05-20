package com.gribiwe.ua.service.dto;

import java.util.Objects;

public class CustomAuthorityDTO {

    private Long id;
    private String name;
    private Long companyId;
    private Long rank;
    private Boolean isManager;
    private Boolean canUploadFiles;
    private Boolean canCreateUser;
    private Boolean canReviewUserList;
    private Boolean canShareFile;
    private Boolean canSeeNeighbor;
    private Boolean canSeeRemovedFiles;
    private Boolean canGrantLowerRoles;
    private Boolean canManageFilesOfTheLowerRoles;

    public CustomAuthorityDTO() {
    }

    public CustomAuthorityDTO(Long id, String name, Long companyId, Long rank, Boolean canUploadFiles, Boolean canCreateUser, Boolean canReviewUserList, Boolean canShareFile, Boolean canSeeNeighbor, Boolean canSeeRemovedFiles, Boolean canGrantLowerRoles, Boolean canManageFilesOfTheLowerRoles) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.rank = rank;
        this.isManager = canUploadFiles;
        this.canUploadFiles = canUploadFiles;
        this.canCreateUser = canCreateUser;
        this.canReviewUserList = canReviewUserList;
        this.canShareFile = canShareFile;
        this.canSeeNeighbor = canSeeNeighbor;
        this.canSeeRemovedFiles = canSeeRemovedFiles;
        this.canGrantLowerRoles = canGrantLowerRoles;
        this.canManageFilesOfTheLowerRoles = canManageFilesOfTheLowerRoles;
    }


    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public Long getId() {
        return id;
    }

    public Boolean getCanUploadFiles() {
        return canUploadFiles;
    }

    public void setCanUploadFiles(Boolean canUploadFiles) {
        this.canUploadFiles = canUploadFiles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomAuthorityDTO that = (CustomAuthorityDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(companyId, that.companyId) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(canUploadFiles, that.canUploadFiles) &&
                Objects.equals(canCreateUser, that.canCreateUser) &&
                Objects.equals(canReviewUserList, that.canReviewUserList) &&
                Objects.equals(canShareFile, that.canShareFile) &&
                Objects.equals(canSeeNeighbor, that.canSeeNeighbor) &&
                Objects.equals(canSeeRemovedFiles, that.canSeeRemovedFiles) &&
                Objects.equals(canGrantLowerRoles, that.canGrantLowerRoles) &&
                Objects.equals(canManageFilesOfTheLowerRoles, that.canManageFilesOfTheLowerRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, companyId, rank, canUploadFiles, canCreateUser, canReviewUserList, canShareFile, canSeeNeighbor, canSeeRemovedFiles, canGrantLowerRoles, canManageFilesOfTheLowerRoles);
    }

    @Override
    public String toString() {
        return "CustomAuthorityDTO{" +
                "name='" + name + '\'' +
                ", companyId=" + companyId +
                ", rank=" + rank +
                ", canCreateUser=" + canCreateUser +
                ", canReviewUserList=" + canReviewUserList +
                ", canShareFile=" + canShareFile +
                ", canSeeNeighbor=" + canSeeNeighbor +
                ", canSeeRemovedFiles=" + canSeeRemovedFiles +
                ", canGrantLowerRoles=" + canGrantLowerRoles +
                ", canManageFilesOfTheLowerRoles=" + canManageFilesOfTheLowerRoles +
                '}';
    }
}
