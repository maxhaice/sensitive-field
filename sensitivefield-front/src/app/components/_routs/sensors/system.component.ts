import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Sensor } from 'src/app/interfaces/sensor';
import { TokenStorageService } from 'src/app/services/auth/token.storage.service';
import { FireAlert } from 'src/app/services/fire/firealert.service';
import { FireCommunication } from 'src/app/services/fire/firecomunication.service';
import {FireSound} from '../../../services/fire/firesound.service';
import {HTTPService} from '../../../services/http.service';

@Component({
  selector: 'sensors',
  templateUrl: 'sensors.component.html',
  styleUrls: ['sensors.component.css'],
  providers: [HTTPService]
})
export class SensorsRout implements OnInit{
  // public geoChart: GoogleChartInterface = {
  //   chartType: 'GeoChart',
  //   dataTable: [
  //     ['Provinces', 'Population (2019)'],
  //     ['UA-05',	8858775],
  //   ],
  //   options: {
  //     region: 'UA', // Europe
  //     height: 216,
  //     width: 350,
  //     displayMode: 'markers', //If you want to highlight some cities
  //     resolution: 'provinces',
  //     colorAxis: {colors: ['#ffc107', '#fd7e14', '#dc3545']},
  //     backgroundColor: '#9cf',
  //     datalessRegionColor: '#f8f9fa',
  //     defaultColor: '#6c757d',
  //   }
  // };
  authority: string;
  sensors: Sensor[];
  online: boolean;
  constructor(private tokenStorage: TokenStorageService, private router: Router, private http: HTTPService) {}
 ngOnInit(): void {
   if (this.tokenStorage.getToken()) {
     this.authority = this.tokenStorage.getAuthorities()[0];
     if ( this.authority ){
       if ( this.authority === 'user'){
         this.router.navigate(['']);
       }else{
        this.onlyOnline();
       }
     }
   }else {
     this.router.navigate(['']);
   }
 }

  onlyOnline(): void {
    if (this.online) {
      this.http.get<Sensor[]>('http://localhost:8080/api/audio-sensors').subscribe(done => {
        this.sensors = done.filter( sen => sen.state);
      }, er => {
        FireAlert.executeError('Помилка підключення до серверу.');
      });
    }
    else {
      this.http.get<Sensor[]>('http://localhost:8080/api/audio-sensors').subscribe(done => {
        this.sensors = done;
      }, er => {
        FireAlert.executeError('Помилка підключення до серверу.');
      });
    }
  }
}
