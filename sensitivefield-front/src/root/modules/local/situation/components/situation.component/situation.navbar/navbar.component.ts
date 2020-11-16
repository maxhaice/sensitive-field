import { HttpClient } from '@angular/common/http';
import {Component, Input, OnInit} from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';

@Component({
  selector: 'situation-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css'],
  providers: [ApiService]
})
export class SituationNavbarComponent implements OnInit{
  @Input() side;
  events: AudioEvent[] = [];
   constructor(private as: ApiService){}
  ngOnInit(): void {
  }
  sidebar(){
    this.side.toggle();
  }
}
