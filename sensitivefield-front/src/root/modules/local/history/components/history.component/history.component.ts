import {AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import { Router } from '@angular/router';
import { merge } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';
import { PageDTO } from 'src/root/services/data-transfer/api/interfaces/page-dto.interface';
@Component({
  // tslint:disable-next-line:component-selector
  selector: 'history',
  templateUrl: 'history.component.html',
  styleUrls: ['history.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HistoryComponent implements OnInit, AfterViewInit{
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  selected_audioEvent: AudioEvent;
  selected: number = 0;

  audio_events: AudioEvent[] = [];
  keys: string[] = ['id', 'timeReal', 'type', 'online'];
  isLoadingResults = true;
  isNotContent = false;
  resultsLength = 0;

  constructor(protected _as: ApiService, private _router: Router) {}

  ngOnInit(): void {
    this._as.getAudioEvents(
      1,
      20, undefined, undefined, 'timeReal', true
    ).subscribe(events => {
      this.audio_events = (events as any).audioEventDTOS;
      this.resultsLength = (events as any).totalPages;
    });
  }
  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._as.getAudioEvents(
            this.paginator.pageIndex + 1,
            20, undefined, undefined, this.sort.active, this.sort.direction.toString() === 'desc'
          );
        }),
        map((data) => {
          this.isLoadingResults = false;
          this.isNotContent = false;
          this.resultsLength = (data as any).totalPages;

          return data;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isNotContent = true;
          return [];
        })
      )
      .subscribe(
        (data) => (this.audio_events = (data as any).audioEventDTOS)
      );
  }
  onTimeLine(event: AudioEvent): void {
    this.selected = 1;
    this.selected_audioEvent = event;
  }
}
