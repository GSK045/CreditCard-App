export interface IMenus {
  id: number;
  name: string;
  icon: string;
  items?: IMenuItems[];
}

export interface IMenuItems {
  name: string;
  path: string;
}





