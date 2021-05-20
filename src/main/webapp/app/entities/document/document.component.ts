import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocument } from 'app/shared/model/document.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DocumentService } from './document.service';
import { DocumentDeleteDialogComponent } from './document-delete-dialog.component';
import {User} from "app/core/user/user.model";
import {UserService} from "app/core/user/user.service";
import {EmployeeService} from "app/entities/employee/employee.service";
import {Employee, IEmployee} from "app/shared/model/employee.model";
import {AccountService} from "app/core/auth/account.service";

@Component({
  selector: 'jhi-document',
  templateUrl: './document.component.html'
})
export class DocumentComponent implements OnInit, OnDestroy {
  documents?: IDocument[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  users = [];
  employee: IEmployee[] = [];
  userEmployee: IEmployee;

  constructor(
    protected documentService: DocumentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected userService: UserService,
    protected accountService: AccountService,
    protected employeeService: EmployeeService,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.documentService
      .query()
      .subscribe(
        (res: HttpResponse<IDocument[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  getEmployeeIdByUSerId(userId: number): number {
    let result = null;

    this.employee.forEach(employee => {
      if (employee.jhiUser === userId) {
        result = employee.id;
      }
    })

    return result;
  }

  ngOnInit(): void {

    this.accountService.identity().subscribe(account => {
      this.employeeService.findByLogin(account.login).subscribe((emplo => {

        this.userEmployee = emplo.body;

        this.activatedRoute.data.subscribe(data => {
          this.page = data.pagingParams.page;
          this.ascending = data.pagingParams.ascending;
          this.predicate = data.pagingParams.predicate;
          this.ngbPaginationPage = data.pagingParams.page;
          this.loadPage();
        });
        this.registerChangeInDocuments();

        this.userService
            .query()
            .subscribe((res: HttpResponse<User[]>) => {
              this.users = res.body;
            });

        this.employeeService
            .query()
            .subscribe((res: HttpResponse<Employee[]>) => {
              this.employee = res.body;
            });
      }))
    })


  }

  getUserNameById(id: number) {
    let result = "";
    this.users.forEach((user => {
      if (user.userDTO.id === id) {
        user = user.employeeDTO
        result = user.firstName + " " + user.lastName;
      }
    }))

    return result;
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDocument): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDocuments(): void {
    this.eventSubscriber = this.eventManager.subscribe('documentListModification', () => this.loadPage());
  }

  delete(document: IDocument): void {
    const modalRef = this.modalService.open(DocumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.document = document;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDocument[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/document'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });

    this.employeeService.query().subscribe(empRes => {


      this.documents = (data || []).filter(doc => {
        let result = false;
        empRes.body.forEach(emp => {
          if (doc.author === emp.jhiUser
              && emp.companyId === this.userEmployee.companyId
          && (this.getMaxRank(emp) < this.getMaxRank(this.userEmployee) ||
                  (this.getMaxRank(emp) <= this.getMaxRank(this.userEmployee) &&
                      this.containsPermission("canSeeNeighbor", this.userEmployee)))) {
            result = true;
          } else if (doc.author === this.userEmployee.jhiUser) {
            result = true
          }
        })

        return result;
      });

    })


  }

  containsPermission(permission: string, emp: IEmployee): boolean {
    let result = false;
    emp.customAuthorities.forEach(aut => {
      if (aut[permission]) {
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

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
