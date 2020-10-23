import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { CommunicationService } from 'src/app/services/app.service';
import {NavigationEnd, Router} from '@angular/router';

export interface Person {
  id: string;
  name: string;
  lastName: string;
  birthday: string;
}
export interface Status {
  person: Person;
  status: boolean;
}
@Component({
  // tslint:disable-next-line:component-selector
  selector: 'visits',
  templateUrl: './visits.component.html',
  styleUrls: ['./visits.component.css']
})
export class VisitsComponent implements OnInit{
  public personWithStatus: Status[] = [];
  public currentTab: string;

  constructor(private http: HttpClient, public service: CommunicationService, private router: Router) {
      this.router.events.subscribe(event => {
        if (event instanceof NavigationEnd) {
          const eventUrl = /(?<=\/).+/.exec(event.urlAfterRedirects);
          this.currentTab = eventUrl[0].split('/')[0];
        }
      });
    }
  public ngOnInit(): void{
    this.http.get<Person[]>('http://localhost:8080/api/persons', {
            headers: {'Access-Control-Allow-Origin': 'GET'}
          })
          .subscribe(all => {
            this.http.get<Person[]>('http://localhost:8080/api/persons/on-site ')
              .subscribe(onSite => {
                  all.forEach(pers => {
                      if (onSite.filter(x => x.id === pers.id).length > 0) {
                        this.personWithStatus.push({
                          person: pers,
                          status: true
                        } as Status);
                      }
                      else {
                        this.personWithStatus.push({
                          person: pers,
                          status: false
                        } as Status);
                      }
                  });
              });
          });

  }

  public changeStatus(status: Status): void {
    if (this.currentTab === 'admin'){
      const now = new Date();
      const day: string = (now.getDay().toString().length === 1) ? 0 + now.getDay().toString() : now.getDay().toString();
      const month: string = (now.getMonth().toString().length === 1) ? 0 + now.getMonth().toString() : now.getMonth().toString();
      const year: string = now.getFullYear().toString();
      this.http.post(('http://localhost:8080/api/' + (status.status ? 'leaves' : 'enters')), {
        person_id: status.person.id,
        date: year + '-' + month + '-' + day,
        time: now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds()
      }, {
        headers: {'Access-Control-Allow-Origin': 'POST'}
      })
        .subscribe(result => {
        }, error => {
          if (error.status === '200'){
            this.personWithStatus[this.personWithStatus.indexOf(status)]
                .status = !this.personWithStatus[this.personWithStatus.indexOf(status)].status;
          }
        });
    }
  }
}
