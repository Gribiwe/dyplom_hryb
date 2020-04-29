import {Employee, IEmployee} from "app/shared/model/employee.model";

export interface IUser {
  userDTO?: JHIUser,
  employeeDTO?: Employee,
}

export class JHIUser {
  public id?: any;
  public login?: string;
  public firstName?: string;
  public lastName?: string;
  public email?: string;
  public activated?: boolean;
  public langKey?: string;
  public authorities?: string[];
  public createdBy?: string;
  public createdDate?: Date;
  public lastModifiedBy?: string;
  public lastModifiedDate?: Date;
  public password?: string;
}

export class User implements IUser {
  constructor(
    public userDTO?: JHIUser,
    public employeeDTO?: IEmployee,
  ) {}
}
