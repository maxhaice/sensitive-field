import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
export interface New{
  id: string;
  date: string;
  title: string;
  url: string;
}
@Component({
  selector: 'news-div',
  templateUrl: 'news.component.html',
  styleUrls: ['news.component.css']
})
export class News implements OnInit{
  public news: New[] = [];
  constructor(private http: HttpClient){}
  ngOnInit(): void {
    this.http.get<New[]>('http://localhost:8080/api/news?created_at=all_time', {
      headers: {'Access-Control-Allow-Origin': 'GET'}
    })
      .subscribe(response => this.news = response);
  }
}
