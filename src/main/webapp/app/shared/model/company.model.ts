import { IDepartment } from 'app/shared/model/department.model';

export interface ICompany {
  id?: number;
  comapnyName?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
}

export class Company implements ICompany {
  constructor(public id?: number,
              public comapnyName?: string,
              streetAddress?: string,
              postalCode?: string,
              city?: string,
              stateProvince?: string,
              public baseDepartmentId?: number, public departments?: IDepartment[]) {
  }
}
