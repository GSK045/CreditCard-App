import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './Components/login/login.component';
import { NavBarComponent } from './Components/nav-bar/nav-bar.component';
import { NavMenuComponent } from './Components/nav-menu/nav-menu.component';
import { HomeComponent } from './Components/home/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card'; //
import { CreditCardRequestComponent } from './Components/credit-card-request/credit-card-request.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { HttpClientModule } from '@angular/common/http';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatOptionModule } from '@angular/material/core';

import { MatDialogModule } from '@angular/material/dialog'; // Import MatDialogModule
import { DisplayCreditCardRequestComponent } from './Components/display-credit-card-request/display-credit-card-request.component';
import { ApproveDialogComponent } from './Components/approvaldialog/approvedialog.component';
import { ViewApproveDialogComponent } from './Components/admin/viewapproval/viewapproval.component';
import { MatSelectModule } from '@angular/material/select';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavBarComponent,
    NavMenuComponent,
    HomeComponent,
    CreditCardRequestComponent,
    DisplayCreditCardRequestComponent,
    ApproveDialogComponent,
    ViewApproveDialogComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MatSelectModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatToolbarModule,
    MatSidenavModule,
    MatOptionModule,
    MatListModule, // Include MatListModule here
    MatIconModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule, // Add FormsModule to imports
    MatMenuModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule, // Ensure MatButtonModule is imported if using buttons
    MatFormFieldModule, // Ensure MatFormFieldModule is imported if using form fields
    MatCardModule, // Ensure MatCardModule is imported
    MatInputModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatPaginatorModule,
    MatTableModule,
    MatTooltipModule,
    MatNativeDateModule, // Include MatNativeDateModule for datepicker to work correctly
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
