import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IMenus } from 'src/app/Models/menus';
import { IUserDto, UserDto } from 'src/app/Models/user';
import { AdminMenus, CustomerMenus } from 'src/app/util/RoleBasedNav';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
})
export class NavBarComponent implements OnInit {
  sidenavOpened = true;
  collapsed = false;
  expandedMenu: number | null = null;
  userData: IUserDto = new UserDto();
  isLoggedIn: boolean = false;
  // user = { userName: 'John Doe' };
  // roleName = 'Admin';
  // imageUrl: string | null = null;

  menuItems: IMenus[] = [];

  ngOnInit(): void {
    const user = localStorage.getItem('user');
    if (user) {
      this.userData = JSON.parse(user);
      this.isLoggedIn = true;
      this.menuItems =
        this.userData.roleName === 'Customer'
          ? this.getCustomerMenuItems()
          : this.getAdminMenuItems();
    } else {
      this.isLoggedIn = false;
    }
  }

  constructor(private router: Router) {}

  toggleSidenav() {
    this.sidenavOpened = !this.sidenavOpened;
  }

  goToHome() {
    this.router.navigate(['/layout']);
  }
  GoToLogin() {
    this.router.navigate(['/login']);
  }
  LogOut() {
    let flag = confirm('Are you sure you want to log out');
    if (flag) {
      localStorage.removeItem('user');
      this.isLoggedIn = false;
      this.menuItems = [];
      this.router.navigate(['/layout']);
    }
  }

  toggleMenu(menuId: number) {
    this.expandedMenu = this.expandedMenu === menuId ? null : menuId;
  }

  getCustomerMenuItems() {
    return CustomerMenus;
  }
  getAdminMenuItems() {
    return AdminMenus;
  }
}
