import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
export interface Log {
  id: number;
  name: string;
  value: string;
  date: string;
  time: string;
}
@Component({
  selector: 'logger-div',
  templateUrl: 'logger.component.html',
  styleUrls: ['logger.component.css']
})
export class Logger implements OnInit{
  public logs: Log[];
  constructor(private http: HttpClient) {
  }

  ngOnInit(): void{
    this.http.get<Log[]>('http://localhost:8080/api/logs', {
    headers: {'Access-Control-Allow-Origin': 'GET'}
    })
      .subscribe(response => this.logs = response);
  }
}
