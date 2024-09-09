import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { HomeComponent } from './Components/home/home/home.component';
import { NavBarComponent } from './Components/nav-bar/nav-bar.component';
import { CreditCardRequestComponent } from './Components/credit-card-request/credit-card-request.component';
import { DisplayCreditCardRequestComponent } from './Components/display-credit-card-request/display-credit-card-request.component';

const routes: Routes = [
  { path: '', redirectTo: '/layout', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },

  {
    path: 'layout',
    component: NavBarComponent,
    children: [
      {
        path: '',
        component: HomeComponent,
      },
      {
        path: 'creditcardrequest',
        component: CreditCardRequestComponent,
      },
      {
        path: 'approvedCardRequest/ACR',
        component: DisplayCreditCardRequestComponent,
      },
      {
        path: 'creditcard',
        component: DisplayCreditCardRequestComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
