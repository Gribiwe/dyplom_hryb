import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import * as moment from 'moment';

import {Account} from 'app/core/user/account.model';
import {IEmployee} from 'app/shared/model/employee.model';
import {EmployeeService} from './employee.service';
import {ICompany} from 'app/shared/model/company.model';
import {CompanyService} from 'app/entities/company/company.service';
import {ICustomAuthority} from "app/shared/model/custom-authority.model";
import {CustomAuthorityService} from "app/entities/employee/custom-authority.service";
import {AccountService} from "app/core/auth/account.service";

type SelectableEntity = IEmployee | ICompany;

@Component({
    selector: 'jhi-employee-update',
    templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
    isSaving = false
    companys: ICompany[] = [];
    roles: ICustomAuthority[] = [];

    oldEmployee: IEmployee;
    employee: IEmployee;

    creator: IEmployee;
    creatorAccount: Account;

    constructor(
        protected employeeService: EmployeeService,
        protected companyService: CompanyService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected customAuthorityService: CustomAuthorityService,
        private fb: FormBuilder
    ) {
    }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({employee}) => {
            if (!employee.id) {
                const today = moment().startOf('day');
                employee.hireDate = today;

                this.oldEmployee = employee;
            }

            this.employee = employee;

            this.accountService.identity().subscribe(account => {
                this.creatorAccount = account;
                this.employeeService.findByLogin(account.login).subscribe((empl => {
                    this.creator = empl.body;

                    this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => {
                        this.companys = res.body || [];

                        if (this.employee.id) {
                            this.companys.forEach(comp => {
                                if (comp.id === this.employee.companyId) {
                                    this.onCompanySelected()
                                }
                            })
                        } else {
                            if (this.hasAdminRole(account)) {
                                this.employee.companyId = this.companys[0].id
                            } else {
                                this.employee.companyId = this.creator.companyId;

                            }
                            this.onCompanySelected()
                        }
                    });
                }))
            })
        });
    }

    hasAdminRole(acc: Account): boolean {
        let result = false;

        acc.authorities.forEach(auth => {
            if (auth === 'ROLE_ADMIN') {
                result = true;
            }
        })

        return result;
    }

    getMaxRank(employee: IEmployee): number {
        let result = 0;

        employee.customAuthorities.forEach(aut => {
            if (aut.rank > result) result = aut.rank;
        })

        return result
    }

    onCompanySelected() {
        this.customAuthorityService.query().subscribe(res => {
            // this.employee.customAuthorities = this.oldEmployee.customAuthorities;
            console.log("asd")

            this.roles = res.body.filter(role => role.companyId === this.employee.companyId &&
                (this.getMaxRank(this.creator) > role.rank || this.hasAdminRole(this.creatorAccount)))

            console.log("=================")
            console.log(this.roles)
            console.log(this.employee)
        })
    }

    previousState(): void {
        window.history.back();
    }

    save(): void {
        this.isSaving = true;
        if (this.employee.id !== undefined) {
            this.subscribeToSaveResponse(this.employeeService.update(this.employee));
        } else {
            this.subscribeToSaveResponse(this.employeeService.create(this.employee));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
        result.subscribe(
            (newEmp) => {
                this.employee.customAuthorities.forEach(authority => {
                    this.employeeService.addRole(newEmp.body.id, authority.id).subscribe(() => {

                    })
                })

                this.onSaveSuccess()
            },
            () => this.onSaveError()
        );
    }

    protected onSaveSuccess(): void {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError(): void {
        this.isSaving = false;
    }

    trackById(index: number, item: SelectableEntity): any {
        return item.id;
    }
}
