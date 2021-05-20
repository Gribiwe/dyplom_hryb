import {Component, OnDestroy, OnInit} from '@angular/core';
import {CompanyService} from "app/entities/company/company.service";
import {ICompany} from "app/shared/model/company.model";
import {CustomAuthority, ICustomAuthority} from "app/shared/model/custom-authority.model";
import {CustomAuthorityService} from "app/entities/employee/custom-authority.service";
import {AccountService} from "app/core/auth/account.service";
import {IEmployee} from "app/shared/model/employee.model";
import {HttpResponse} from "@angular/common/http";
import {EmployeeService} from "app/entities/employee/employee.service";

@Component({
    selector: 'jhi-role',
    templateUrl: './role.component.html'
})
export class RoleComponent implements OnInit, OnDestroy {

    companies: ICompany [] = [];
    selectedCompany: ICompany;
    selectedRole: ICustomAuthority;
    selectedIndex: number = 0;
    roles: ICustomAuthority [] = [];

    allRoles: ICustomAuthority [] = [];
    employee: IEmployee;

    isSaving: boolean = false;
    message: string = "";

    maxRank: number = 0;

    constructor(
        private companyService: CompanyService,
        private customAuthorityService: CustomAuthorityService,
        private accountService: AccountService,
        private employeeService: EmployeeService,
    ) {

    }

    ngOnDestroy(): void {
    }

    ngOnInit(): void {


        this.accountService.identity().subscribe((account) => {

            console.log("acc")

            this.employeeService.findByLogin(account.login).subscribe((res: HttpResponse<IEmployee>) => {
                this.employee = res.body

                console.log("emp")
                this.companyService.query().subscribe(res => {
                    this.companies = res.body

                    console.log("cmps")
                    this.customAuthorityService.query().subscribe(res => {
                        this.allRoles = res.body

                        console.log(this.allRoles)

                        this.companies.forEach(company => {
                            if (this.employee.companyId === company.id) {

                                console.log("company found")

                                this.selectedCompany = company;
                                this.companyChanged();

                                this.employee.customAuthorities.forEach(aut => {
                                    if (aut.rank > this.maxRank) this.maxRank = aut.rank;
                                })
                            }
                        })
                    })
                })
            })
        })
    }

    companyChanged() {
        if (!this.isSaving) {
            this.roles = this.allRoles.filter(role => role.companyId === this.selectedCompany.id)
            this.selectedRole = this.roles[0];
        }
    }

    addNewRole() {
        let customAuthority = new CustomAuthority();
        customAuthority.name = "Нова роль"
        customAuthority.companyId = this.selectedCompany.id;
        customAuthority.rank = 1;

        this.roles.push(customAuthority);
        this.selectRole(customAuthority, this.roles.length-1);
    }

    selectRole(customAuthority: ICustomAuthority, i: number) {

        this.selectedRole = customAuthority;
        this.selectedIndex = i;
    }

    isTheHighestRole(): boolean {
        if (this.selectedRole.manager) return true;

        return this.selectedRole.rank === this.maxRank
    }

    save() {
        this.isSaving = true;
        let saved = 0;

        this.roles.forEach(role => {

            if (role.id) {
                this.customAuthorityService.update(role).subscribe(() => {
                    saved++;

                    if (saved === this.roles.length) {
                        this.isSaving = false;
                        this.ngOnInit();
                        this.message = "Збережено! ";
                        setTimeout(() => {
                            this.message = ""
                        }, 5000)
                    }
                })
            } else {
                this.customAuthorityService.create(role).subscribe(() => {
                    saved++;

                    if (saved === this.roles.length) {
                        this.isSaving = false;
                        this.ngOnInit();
                        this.message = "Збережено! ";
                        setTimeout(() => {
                            this.message = ""
                        }, 5000)
                    }
                })
            }
        })
    }

    remove() {
        this.isSaving = true;

        if (this.selectedRole.id) {

            this.customAuthorityService.delete(this.selectedRole.id).subscribe(() => {
                this.isSaving = false;
                this.roles = this.roles.filter(role => role !== this.selectedRole);
                this.message = "Роль видалено! ";

                this.selectedIndex = this.selectedIndex > this.roles.length-1? this.roles.length-1: this.selectedIndex;

                this.selectRole(this.roles[this.selectedIndex], this.selectedIndex)
                setTimeout(() => {
                    this.message = ""
                }, 5000)
            })
        } else {
            this.roles = this.roles.filter(role => role !== this.selectedRole);
            this.isSaving = false;

            this.selectedIndex = this.selectedIndex > this.roles.length-1? this.roles.length-1: this.selectedIndex;

            this.selectRole(this.roles[this.selectedIndex], this.selectedIndex)
            this.message = "Роль видалено! ";
            setTimeout(() => {
                this.message = ""
            }, 5000)
        }




    }
}
