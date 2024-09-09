import { IMenus } from '../Models/menus';

export const CustomerMenus: IMenus[] = [
  {
    id: 1,
    name: 'Cards',
    icon: 'dashboard',
    items: [
      {
        name: 'Credit Card',
        path: '/layout/creditcard',
      },
    ],
  },
];

export const AdminMenus: IMenus[] = [
  {
    id: 1,
    name: 'Card Request',
    icon: 'dashboard',
    items: [
      {
        name: 'Credit Card Requests',
        path: '/layout/creditcard',
      }
     
    ],
  },
];
