import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../../local-storage/token.storage.service';

@Injectable({
    providedIn: 'root'
})
export class HTTPService{
  private originalUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient, private tokenStorage: TokenStorageService){}
  post<T>(url: string, body: any): Observable<T>{
    return this.http.post<T>(this.originalUrl + url, body,
      { headers: {'Access-Control-Allow-Origin': 'POST', Authorization: 'Bearer ' + this.tokenStorage.getToken() }});
  }
  get<T>(url: string): Observable<T>{
    return this.http.get<T>(this.originalUrl + url,
      { headers: {'Access-Control-Allow-Origin': 'GET', Authorization: 'Bearer ' + this.tokenStorage.getToken() }});
  }
}
