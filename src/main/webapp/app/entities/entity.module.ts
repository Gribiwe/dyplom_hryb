import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document',
        loadChildren: () => import('./document/document.module').then(m => m.GpaperDocumentModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.GpaperDepartmentModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.GpaperCompanyModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.GpaperEmployeeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GpaperEntityModule {}
