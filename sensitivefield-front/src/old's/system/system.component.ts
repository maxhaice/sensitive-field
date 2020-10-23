import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
export interface ISystem {
  date: string;
  time: string;
  cpu: string;
  ram: string;
}
@Component({
  selector: 'system',
  templateUrl: 'system.component.html',
  styleUrls: ['system.component.css']
})
export class System implements OnInit{
  public system: ISystem[];
  constructor(private http: HttpClient) {
  }
  ngOnInit(): void {
    this.http.get<ISystem[]>('http://localhost:8080/api/systems', {
      headers: {'Access-Control-Allow-Origin': 'GET'}
    })
      .subscribe(response => this.system = response);
  }
}
