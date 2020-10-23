import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ISchedule} from '../schedule/schedule.component';
@Component({
  selector: 'loader',
  templateUrl: 'loader.component.html',
  styleUrls: ['loader.component.css']
})
export class LoaderComponent implements OnInit{
  public status = '';
  constructor(private http: HttpClient){}
  ngOnInit(): void {
  }
  public handleFileInput(files: FileList): void {

    const formData = new FormData(); formData.append('file', files[0], files[0].name);
    this.http.post('http://localhost:8080/api/timetable/fill', formData).subscribe(response => {}, error => {
      if (error.status){
        this.status = 'Парсинг удался!';
      }
    });
  }
}
