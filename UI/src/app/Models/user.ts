export interface IUserDto {
  id: number;
  email: string;
  username: string;
  roleId: number;
  roleName: string;
}

export class UserDto implements IUserDto {
  id: number = 0;
  email: string = '';
  username: string = '';
  roleId: number = 0;
  roleName: string = '';
}

export interface ILogin {
  email: string;
  password: string;
}

export class Login {
  email: string = '';
  password: string = '';
}
