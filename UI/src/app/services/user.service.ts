import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LOGIN } from '../apis/apis';
import { ILogin, IUserDto } from '../Models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  //adding reports
  loginRequest(loginData: ILogin): Observable<any> {
    console.log(loginData);
    return this.http.post<any>(
      `http://localhost:1001/api/credit/user/login`,
      loginData
    );
  }
}
