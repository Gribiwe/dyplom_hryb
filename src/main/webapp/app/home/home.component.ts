import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import {EmployeeService} from "app/entities/employee/employee.service";
import {CompanyService} from "app/entities/company/company.service";
import {DepartmentService} from "app/entities/department/department.service";
import {DocumentService} from "app/entities/document/document.service";
import {HttpHeaders, HttpResponse} from "@angular/common/http";
import {IEmployee} from "app/shared/model/employee.model";
import {IDocument} from "app/shared/model/document.model";
import {IDepartment} from "app/shared/model/department.model";
import {ICompany} from "app/shared/model/company.model";

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  employeeNumber: number = 0;
  companyNumber: number = 0;
  departmentNumber: number = 0;
  documentNumber: number = 0;
  imageUrl: any = "";

  constructor(
    private accountService: AccountService,
    private employeeService: EmployeeService,
    private companyService: CompanyService,
    private departmentService: DepartmentService,
    private documentService: DocumentService,
              private loginModalService: LoginModalService) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      if (account !== null) {
        this.imageUrl = 'api/documents/avatar/'+account.login;
      }

      this.employeeService
        .query({
          page: 1,
          size: 20,
          sort: ['id', 'asc']
        })
        .subscribe((res: HttpResponse<IEmployee[]>) => this.employees(res.headers));

      this.documentService
        .query({
          page: 1,
          size: 20,
          sort: ['id', 'asc']
        })
        .subscribe(
          (res: HttpResponse<IDocument[]>) => this.documents(res.headers),
          () => {}
        );

      this.departmentService
        .query({
          page: 1,
          size: 20,
          sort: ['id', 'asc']
        })
        .subscribe((res: HttpResponse<IDepartment[]>) => this.departments(res.headers));

      this.companyService
        .query({
          page: 1,
          size: 20,
          sort: ['id', 'asc']
        })
        .subscribe((res: HttpResponse<ICompany[]>) => this.companies(res.headers));
    });
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  private employees(headers: HttpHeaders) {
    let res = Number(headers.get('X-Total-Count'));
    this.employeeNumber = 0
    let interval = setInterval(() => {
      if (this.employeeNumber == res) {
        clearInterval(interval)
      } else {
        this.employeeNumber++;
      }
    }, 2000 / res)
  }

  private documents(headers: HttpHeaders) {
    let res = Number(headers.get('X-Total-Count'));
    this.documentNumber = 0
    let interval = setInterval(() => {
      if (this.documentNumber == res) {
        clearInterval(interval)
      } else {
        this.documentNumber++;
      }
    }, 2000 / res)
  }

  private companies(headers: HttpHeaders) {
    let res = Number(headers.get('X-Total-Count'));
    this.companyNumber = 0
    let interval = setInterval(() => {
      if (this.companyNumber == res) {
        clearInterval(interval)
      } else {
        this.companyNumber++;
      }
    }, 2000 / res)
  }

  private departments(headers: HttpHeaders) {
    let res = Number(headers.get('X-Total-Count'));
    this.departmentNumber = 0
    let interval = setInterval(() => {
      if (this.departmentNumber == res) {
        clearInterval(interval)
      } else {
        this.departmentNumber++;
      }
    }, 2000 / res)
  }
}
