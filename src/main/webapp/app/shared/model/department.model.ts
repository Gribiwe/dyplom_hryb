import { IEmployee } from 'app/shared/model/employee.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  employees?: IEmployee[];
  companyId?: number;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public departmentName?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public employees?: IEmployee[],
    public companyId?: number
  ) {}
}
