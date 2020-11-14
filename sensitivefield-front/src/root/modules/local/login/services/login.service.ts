import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginInfo } from '../interfaces/logininfo.interface';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loginUrl = 'http://localhost:8080/api/auth/signin';
  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: LoginInfo): Observable<any> {
    return this.http.post(this.loginUrl, credentials, {  headers: {'Access-Control-Allow-Origin': 'GET'}});
  }

}
