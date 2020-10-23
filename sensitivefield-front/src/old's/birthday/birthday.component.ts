import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
export interface Birth{
  name: string;
  lastName: string;
  birthday: string;
}
@Component({
  selector: 'birthday',
  templateUrl: 'birthday.component.html',
  styleUrls: ['birthday.component.css']
})
export class Birthday implements OnInit {
  public birthdays: Birth[] = [];
  constructor(private http: HttpClient){}
  ngOnInit(): void {
    this.http.get<Birth[]>('http://localhost:8080/api/birthdays?date=this_month', {
      headers: {'Access-Control-Allow-Origin': 'GET'}
    })
      .subscribe(response => this.birthdays = response);
  }

}
