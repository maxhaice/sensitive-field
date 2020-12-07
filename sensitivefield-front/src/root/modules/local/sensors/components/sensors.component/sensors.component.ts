import {Component, ViewChild, AfterViewInit, OnInit} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import {MatSort, SortDirection} from '@angular/material/sort';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { merge } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import { PageDTO } from 'src/root/services/data-transfer/api/interfaces/page-dto.interface';

@Component({
  selector: 'sensors',
  templateUrl: 'sensors.component.html',
  styleUrls: ['sensors.component.css'],
})
export class SensorsComponent implements OnInit, AfterViewInit {


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  keys: string[] = ['id', 'latitude', 'longitude', 'map'];
  isLoadingResults = true;
  isNotContent = false;
  resultsLength = 0;
  audio_sensors: AudioSensor[] = [];

  constructor(protected _as: ApiService, private _router: Router) {
  }

  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._as.getAudioSensors
          ( this.paginator.pageIndex + 1, 20, this.sort.active, (this.sort.direction as SortDirection).toString() === 'desk');
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isNotContent = false;
          this.resultsLength = (data.meta as any).totalPages * 20;

          return data.audioSensors;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isNotContent = true;
          return [];
        })
      ).subscribe(data => this.audio_sensors = (data as PageDTO<AudioSensor[]>).audioSensors);
    }

  ngOnInit(): void {
    this._as.getAudioSensors
    (1, 20, 'latitude', true)
      .subscribe(sensors => {
        this.audio_sensors = sensors.audioSensors;
        this.resultsLength = (sensors as any).totalPages;
      });
  }

  toMap(ausio_sensor: AudioSensor): void {
    this._router.navigate(['situation', {lat: ausio_sensor.latitude, lon: ausio_sensor.longitude}]);
  }
}
