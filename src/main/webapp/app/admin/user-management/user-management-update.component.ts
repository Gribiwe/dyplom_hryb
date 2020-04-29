import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';

import {JHIUser, User} from 'app/core/user/user.model';
import {UserService} from 'app/core/user/user.service';
import {Employee, IEmployee} from "app/shared/model/employee.model";
import {IDepartment} from "app/shared/model/department.model";
import {EmployeeService} from "app/entities/employee/employee.service";
import {DepartmentService} from "app/entities/department/department.service";
import {HttpResponse} from "@angular/common/http";
import {DATE_TIME_FORMAT} from "app/shared/constants/input.constants";
import * as moment from "moment";

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserManagementUpdateComponent implements OnInit {
  user!: JHIUser;
  authorities: string[] = [];
  isSaving = false;
  employees: IEmployee[] = [];
  departments: IDepartment[] = [];

  // @ts-ignore
  fullDTO: User;

  // @ts-ignore
  employee;

  editForm = this.fb.group({
    id: [],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [],
    authorities: [],
    langKey: [],
    hireDate: [],
    phoneNumber: [],
    managerId: [],
    salary: [],
    departmentId: []
  });

  constructor(  protected employeeService: EmployeeService,
                protected departmentService: DepartmentService,
                private userService: UserService,
                private route: ActivatedRoute,
                private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(({user}) => {
      if (user) {
        if (!user.userDTO) {
          user.userDTO = new JHIUser()
        }
        if (!user.employeeDTO) {
          user.employeeDTO = new Employee()
        }
        this.fullDTO = user;
        // @ts-ignore
        this.user = this.fullDTO.userDTO;
        if (this.user.id === undefined) {
          this.user.activated = true;
        }
        this.updateForm(user);

        this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

        this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      }
    });
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.fullDTO.userDTO = this.user;
    // @ts-ignore
    this.updateUser(this.fullDTO);
    if (this.fullDTO.userDTO.id !== undefined) {
      this.userService.update(this.fullDTO).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.user.langKey = 'en';
      this.userService.create(this.fullDTO).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
  }

  private updateForm(user: User): void {

    this.editForm.patchValue({
      // @ts-ignore
      id: user.userDTO.id,
      // @ts-ignore
      login: user.userDTO.login,
      // @ts-ignore
      firstName: user.userDTO.firstName,
      // @ts-ignore
      lastName: user.userDTO.lastName,
      // @ts-ignore
      email: user.userDTO.email,
      // @ts-ignore
      activated: user.userDTO.activated,
      // @ts-ignore
      langKey: user.userDTO.langKey,
      // @ts-ignore
      authorities: user.userDTO.authorities,
      // @ts-ignore
      hireDate: user.employeeDTO.hireDate,
      // @ts-ignore
      phoneNumber: user.employeeDTO.phoneNumber,
      // @ts-ignore
      managerId: user.employeeDTO.managerId,
      // @ts-ignore
      salary: user.employeeDTO.salary,
      // @ts-ignore
      departmentId: user.employeeDTO.departmentId,
    });
  }

  private updateUser(user: User): void {
    if (!user.userDTO) {
      user.userDTO = new JHIUser()
    }
    if (!user.employeeDTO) {
      user.employeeDTO = new Employee()
    }
    // @ts-ignore
    user.userDTO.login = this.editForm.get(['login'])!.value;
    // @ts-ignore
    user.userDTO.firstName = this.editForm.get(['firstName'])!.value;
    // @ts-ignore
    user.userDTO.lastName = this.editForm.get(['lastName'])!.value;
    // @ts-ignore
    user.userDTO.email = this.editForm.get(['email'])!.value;
    // @ts-ignore
    user.userDTO.activated = this.editForm.get(['activated'])!.value;
    // @ts-ignore
    user.userDTO.langKey = this.editForm.get(['langKey'])!.value;
    // @ts-ignore
    user.userDTO.authorities = this.editForm.get(['authorities'])!.value;
    // @ts-ignore
    user.employeeDTO.hireDate = moment(this.editForm.get(['hireDate'])!.value, DATE_TIME_FORMAT);
    // @ts-ignore
    user.employeeDTO.phoneNumber = this.editForm.get(['phoneNumber'])!.value;
    // @ts-ignore
    user.employeeDTO.managerId = this.editForm.get(['managerId'])!.value;
    // @ts-ignore
    user.employeeDTO.salary = this.editForm.get(['salary'])!.value;
    // @ts-ignore
    user.employeeDTO.departmentId = this.editForm.get(['departmentId'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
