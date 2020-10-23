import { HttpClient } from '@angular/common/http';
import {Injectable, OnInit, Type} from '@angular/core';
import {Observable} from 'rxjs';
import {FireSound} from './fire/firesound.service';
import {TokenStorageService} from './auth/token.storage.service';

@Injectable()
export class HTTPService{
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService){}
  post<T>(url: string, body: any): Observable<T>{
    return this.http.post<T>(url, body,
      { headers: {'Access-Control-Allow-Origin': 'POST', Authorization: 'Bearer ' + this.tokenStorage.getToken() }});
  }
  get<T>(url: string): Observable<T>{
    return this.http.get<T>(url,
      { headers: {'Access-Control-Allow-Origin': 'GET', Authorization: 'Bearer ' + this.tokenStorage.getToken() }});
  }
}
