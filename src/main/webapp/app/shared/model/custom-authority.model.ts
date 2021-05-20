export interface ICustomAuthority {
  id?: number;
  name?: string;
  companyId?: number;
  rank?: number;
  manager?: boolean;
  canUploadFiles?: boolean;
  canCreateUser?: boolean;
  canReviewUserList?: boolean;
  canShareFile?: boolean;
  canSeeNeighbor?: boolean;
  canSeeRemovedFiles?: boolean;
  canGrantLowerRoles?: boolean;
  canManageFilesOfTheLowerRoles?: boolean;
}

export class CustomAuthority implements ICustomAuthority {
  constructor(public id?: number,
              public name?: string,
              public companyId?: number,
              public rank?: number,
              public manager?: boolean,
              public canCreateUser?: boolean,
              public canUploadFiles?: boolean,
              public canReviewUserList?: boolean,
              public canShareFile?: boolean,
              public canSeeNeighbor?: boolean,
              public canSeeRemovedFiles?: boolean,
              public canGrantLowerRoles?: boolean,
              public canManageFilesOfTheLowerRoles?: boolean
  ) {
  }
}
