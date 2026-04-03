export type News = {
  newsId: number;
  title: string;
  content: string;
  imgPath: string;
  startDate: string;
  endDate: string;
  delFlag: number;
  createId: string;
  createDate: string;
  updateId: string;
  updateDate: string;
};

export type UsersItem = {
  imageFile: File,
  associateId: string;
  username: string;
  firstName: string;
  middleName: string;
  lastName: string;
  positionId: number;
  departmentId: number;
  businessUnitId: number;
  statusId: number;
  roleId: number;
  email: string;
  lockFlag: number;
};

 export interface userList {
    user_id: number;
    account_id: string;
    first_name: string;
    last_name: string;
    position_sh_name: string;
    mailaddress: string;
    section_sh_name: string;
    department_sh_name: string;
    create_date: Date;
}

export type Role = {
    roleId: number;
    roleName: string;
    roleShName: string;
    createId: string;
    createDate: string;
    updateId: string;
    updateDate: string;
  };

  export type Function = {
    functionId: number;
    functionCode: string;
    functionName: string;
    moduleCode: string;
    tier: number;
    icodId: string;
    iconUrl: string;
    displayIndex: string;
    del_flag: number;
    createId: string;
    createDate: string;
    updateId: string;
    updateDate: string;
  };

  export type RoleItem = {
    roleId: string;
    roleName: string;
    roleShName: string;
    permissions: string[];
    createId: string;
    create_date: Date;
  };

  export type Permission = {
    permissionId: number;
    functionCode: string[];
    roleId: number;
  }

//pagination
export interface PageRequest {
  page: number;
  size: number;
  totalItems: number;
}

export type NewsListItem = {
  news_id: string,
  title: string,
  content: string,
  image_path: string,
  start_date: string,
  end_date: string,
  del_flag: string,
  create_id: string,
  create_date: string,
  update_id: string,
  update_date: string
};

 export type Users = {
    userId: number;
    accountId: string;
    firstName: string;
    lastName: string;
    positionShName: string;
    mailddress: string;
    sectionShName: string;
    departmentShName: string;
    createDate: Date;
}
