import {Component, OnInit, ViewChild} from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, BaseChartDirective, Label } from 'ng2-charts';
import {Event} from '../../../interfaces/event';
import {TokenStorageService} from '../../../services/auth/token.storage.service';
import {Router} from '@angular/router';
import {HTTPService} from '../../../services/http.service';
import {FireSound} from '../../../services/fire/firesound.service';
import { FireAlert } from 'src/app/services/fire/firealert.service';
@Component({
  selector: 'statistic',
  templateUrl: 'statistic.component.html',
  styleUrls: ['statistic.component.css'],
  providers: [HTTPService]
})
export class StatisticRout implements OnInit{
  constructor(private tokenStorage: TokenStorageService, private router: Router, private http: HTTPService){}
  public chartType: string;
  public chartMeaning: string;
  public chartEventMeaning: string;
  public chartSensorMeaning: string;
  public chartEventMeaningValue: string;


























  //////////////////////////
  public pieChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      position: 'top',
    },
    plugins: {
      datalabels: {
        formatter: (value, ctx) => {
          const label = ctx.chart.data.labels[ctx.dataIndex];
          return label;
        },
      },
    }
  };
  public pieChartLabels: Label[] = [];
  public pieChartData: number[] = [];
  public pieChartType: ChartType = 'pie';
  public  pieChartPlugins;
  public pieChartLegend = true;
  public pieChartColors = [
    {
      backgroundColor: [],
    },
  ];

  @ViewChild(BaseChartDirective, { static: true }) chart: BaseChartDirective;
  public authority: string;


  events: Event[];
  type = 'Transport';
  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      this.http.get<Event[]>('http://localhost:8080/api/audio-events').subscribe(
        next => {
          this.events = next;
          this.setData(this.events);
        }, error => {
          FireAlert.executeError('Не вдалось встановити з\'єднання з сервером.');
        }
      );
    }
    else {
      // this.router.navigate(['']);
    }
  }
  private setData(events: Event[]): void {
   const times =  events.map( ev => ev.dateReal.split(' ')[1].split(':')[0] + ':' + ev.dateReal.split(' ')[1].split(':')[1]);
   times.filter(this.onlyUnique).forEach( value => {
     this.pieChartLabels.push(value);
     this.pieChartData.push(
       times.filter(x => x === value ).length
     );
     this.pieChartColors[0].backgroundColor.push('rgba(196,79,244,0.3)');
   });
  }
  onlyUnique(value, index, self): boolean {
    return self.indexOf(value) === index;
  }
}
