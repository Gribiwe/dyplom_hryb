import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;
  basedepartments: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    comapnyName: [null, [Validators.required]],
    baseDepartmentId: []
  });

  constructor(
    protected companyService: CompanyService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);

      this.departmentService
        .query({ filter: 'company-is-null' })
        .pipe(
          map((res: HttpResponse<IDepartment[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDepartment[]) => {
          if (!company.baseDepartmentId) {
            this.basedepartments = resBody;
          } else {
            this.departmentService
              .find(company.baseDepartmentId)
              .pipe(
                map((subRes: HttpResponse<IDepartment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDepartment[]) => (this.basedepartments = concatRes));
          }
        });
    });
  }

  updateForm(company: ICompany): void {
    this.editForm.patchValue({
      id: company.id,
      comapnyName: company.comapnyName,
      baseDepartmentId: company.baseDepartmentId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id'])!.value,
      comapnyName: this.editForm.get(['comapnyName'])!.value,
      baseDepartmentId: this.editForm.get(['baseDepartmentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
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

  trackById(index: number, item: IDepartment): any {
    return item.id;
  }
}
