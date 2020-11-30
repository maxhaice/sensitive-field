import {Component, ViewChild, AfterViewInit, OnInit} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { merge } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { Leaflet, LeafletService } from 'src/root/modules/public/map/services/leaflet.service';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import { PageDTO } from 'src/root/services/data-transfer/api/interfaces/page-dto.interface';

@Component({
  selector: 'sensors',
  templateUrl: 'sensors.component.html',
  styleUrls: ['sensors.component.css'],
})
export class SensorsComponent implements OnInit{

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  audio_sensors: AudioSensor[] = [];
  keys: string[] = ['id','latitude','longitude', 'map'];
  isLoadingResults = true;
  isNotContent = false;
  resultsLength = 0;

  constructor(protected _as: ApiService, private _router: Router, private _ls: LeafletService){}

  ngOnInit(): void {}
  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._as.getAudioSensors( this.paginator.pageIndex, undefined, this.sort.active, this.sort.direction === 'desc');
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isNotContent = false;
          this.resultsLength = data.meta.total_items_count;

          return data.data;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isNotContent = true;
          return [];
        })
      ).subscribe(data => this.audio_sensors = (data as PageDTO<AudioSensor[]>).data);

  }
  toMap(ausio_sensor: AudioSensor ): void{
    this._router.navigate(['situation', { lat:ausio_sensor.latitude, lon: ausio_sensor.longitude }]);
  }
}