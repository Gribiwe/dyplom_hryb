import { IDepartment } from 'app/shared/model/department.model';

export interface ICompany {
  id?: number;
  comapnyName?: string;
  baseDepartmentId?: number;
  departments?: IDepartment[];
}

export class Company implements ICompany {
  constructor(public id?: number, public comapnyName?: string, public baseDepartmentId?: number, public departments?: IDepartment[]) {}
}
