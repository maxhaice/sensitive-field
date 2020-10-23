import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginInfo } from 'src/app/interfaces/login';
import {HTTPService} from '../http.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8080/api/auth/signin';
  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: LoginInfo): Observable<any> {
    return this.http.post(this.loginUrl, credentials, {  headers: {'Access-Control-Allow-Origin': 'GET'}});
  }

}
