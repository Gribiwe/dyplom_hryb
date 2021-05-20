import { Moment } from 'moment';
import {ICustomAuthority} from "app/shared/model/custom-authority.model";

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  companyId?: number;
  jhiUser?: number;
  customAuthorities?: ICustomAuthority[];
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
    public companyId?: number,
    public jhiUser?: number,
    public customAuthorities?: ICustomAuthority[]
  ) {}
}
