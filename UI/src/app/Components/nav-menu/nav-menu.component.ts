import { Component } from '@angular/core';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {
  items:any[]=[
    {
    menuName:"Home",
    menuPath:"home",

  },
  {
    menuName:"About",
    menuPath:"about",

  },
  {
    menuName:"Leads",
    menuPath:"lead"

  },
  {
    menuName:"Project",
    menuPath:"project"
  }
]
}
