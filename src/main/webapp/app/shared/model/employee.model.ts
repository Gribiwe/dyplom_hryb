import { Moment } from 'moment';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  managerId?: number;
  departmentId?: number;
  jhiUser?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public salary?: number,
    public managerId?: number,
    public departmentId?: number,
    public jhiUser?: number
  ) {}
}
