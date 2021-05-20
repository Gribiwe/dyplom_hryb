import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpaperSharedModule } from 'app/shared/shared.module';
import { EmployeeComponent } from './employee.component';
import { EmployeeDetailComponent } from './employee-detail.component';
import { EmployeeUpdateComponent } from './employee-update.component';
import { EmployeeDeleteDialogComponent } from './employee-delete-dialog.component';
import { employeeRoute } from './employee.route';
import {RoleComponent} from "app/entities/employee/role.component";

@NgModule({
  imports: [GpaperSharedModule, RouterModule.forChild(employeeRoute)],
  declarations: [EmployeeComponent, EmployeeDetailComponent, EmployeeUpdateComponent, EmployeeDeleteDialogComponent, RoleComponent],
  entryComponents: [EmployeeDeleteDialogComponent]
})
export class GpaperEmployeeModule {}
