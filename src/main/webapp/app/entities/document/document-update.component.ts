import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpEventType, HttpResponse} from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {Observable, of} from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import {SERVER_API_URL} from "app/app.constants";
import {FileUploader} from "ng2-file-upload";
import {catchError, map} from "rxjs/operators";
import {IEmployee} from "app/shared/model/employee.model";
import {EmployeeService} from "app/entities/employee/employee.service";

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];
  employee: IEmployee[] = [];
  uploadURL = SERVER_API_URL+"api/documents/upload";

  editForm = this.fb.group({
    id: [],
    documentName: [],
    startDate: [],
    endDate: [],
    author: [],
  });

  // @ts-ignore
  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;
  files  = [];

  constructor(
    protected documentService: DocumentService,
    protected employeeService: EmployeeService,
    protected httpClient: HttpClient,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  private uploadFiles() {
    this.fileUpload.nativeElement.value = '';
    this.files.forEach(file => {
      this.uploadFile(file);
    });
  }

  onClick() {
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      for (let index = 0; index < fileUpload.files.length; index++)
      {
        const file = fileUpload.files[index];
        // @ts-ignore
        this.files.push({ data: file, inProgress: false, progress: 0});
      }
      this.uploadFiles();
    };
    fileUpload.click();
  }

  // @ts-ignore
  uploadFile(file) {
    const formData = new FormData();
    formData.append('file', file.data);
    file.inProgress = true;
    // @ts-ignore
    // @ts-ignore
    this.upload(formData).pipe(map(event => {
        // @ts-ignore
        switch (event.type) {
          case HttpEventType.UploadProgress:
            // @ts-ignore
            file.progress = Math.round(event.loaded * 100 / event.total);
            break;
          case HttpEventType.Response:
            return event;
        }
      }),
      catchError((error: HttpErrorResponse) => {
        file.inProgress = false;
        return of(`${file.data.name} upload failed.`);
      })).subscribe((event: any) => {
      if (typeof (event) === 'object') {
        console.log(event.body);
        this.fileName = event.body.UUID;
        console.log("FILE");
        console.log(this.fileName);
      }
    });
  }

  fileName: any;

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ document }) => {
      if (!document.id) {
        const today = moment().startOf('day');
        document.startDate = today;
        document.endDate = today;
      } else {
        this.fileName = document.fileName;
      }

      this.updateForm(document);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employee = res.body || []));
    });
  }

  // @ts-ignore
  public upload(formData) {

    return this.httpClient.post<any>(this.uploadURL, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }

  updateForm(document: IDocument): void {
    this.editForm.patchValue({
      id: document.id,
      documentName: document.documentName,
      fileName: this.fileName,
      startDate: document.startDate ? document.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: document.endDate ? document.endDate.format(DATE_TIME_FORMAT) : null,
      author: document.author,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const document = this.createFromForm();
    console.log(document)
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document));
    }
  }

  private createFromForm(): IDocument {
    return {
      ...new Document(),
      id: this.editForm.get(['id'])!.value,
      documentName: this.editForm.get(['documentName'])!.value,
      fileName: this.fileName,
      startDate: moment(new Date(), DATE_TIME_FORMAT),
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      author: this.editForm.get(['author'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>): void {
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
