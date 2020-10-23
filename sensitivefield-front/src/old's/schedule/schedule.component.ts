import { HttpClient } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
export interface ISchedule {
  numberOfLesson: string;
  professorName: string;
  groupNumber: string;
  auditoryName: string;
  disciplineName: string;
  date: string;
}
@Component({
  selector: 'schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class Schedule implements OnInit{
  public dateTimeNow: string = new Date().toDateString();
  public schedule: ISchedule[];
  public today: [ISchedule[], ISchedule[], ISchedule[], ISchedule[]] = [[], [], [], []];
  public auditors = [];
  constructor(private http: HttpClient) {
  }
  ngOnInit(): void {
    this.http.get<ISchedule[]>('http://localhost:8080/api/timetable?date=?today', {
      headers: {'Access-Control-Allow-Origin': 'GET'}
    }).subscribe(response => {
      this.schedule = response;
      this.generateSchedule();
      });
  }
  public generateSchedule(): void{
    this.schedule.map((value) => value.auditoryName).forEach((value => {
      if (!this.auditors.includes(value)) {
        this.auditors.push(value);
  }}));
    this.schedule.forEach((value, index, array) => {
      if (this.today[0].filter(value1 => value1.auditoryName === value.auditoryName).length < 1){
        this.today[0].push(value);
      }
      else if (this.today[1].filter(value1 => value1.auditoryName === value.auditoryName).length < 1){
        this.today[1].push(value);
      }
      else if (this.today[2].filter(value1 => value1.auditoryName === value.auditoryName).length < 1){
        this.today[2].push(value);
      }
      else if (this.today[3].filter(value1 => value1.auditoryName === value.auditoryName).length < 1){
        this.today[3].push(value);
      }
    });
  }
}
