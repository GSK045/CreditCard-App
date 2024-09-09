import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ILogin, Login } from 'src/app/Models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  formData!: FormGroup;
  loginData: ILogin = new Login();

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private userService: UserService
  ) {}
  ngOnInit(): void {
    this.initializeFormData();
  }
  //initialize form data
  private initializeFormData(): void {
    this.formData = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }
  saveLogin() {
    console.log('Form submitted {}', this.formData);
    if (this.formData.valid) {
      this.loginData.email = this.formData.value.email;
      this.loginData.password = this.formData.value.password;
      this.userService.loginRequest(this.loginData).subscribe({
        next: (data) => {
          console.log('Success', data);
          localStorage.setItem('user', JSON.stringify(data));
          this.router.navigate(['/layout']);
        },
        error: (error) => {
          console.error('Error', error);
        },
      });
    }

    // this.router.navigate(['home']);
  }
}
