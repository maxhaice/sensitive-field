import { HttpClient } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import { Sensor } from 'src/app/interfaces/sensor';
import { WebSocketAPI } from 'src/app/services/websocket.connector.service';

import {Event} from '../../../interfaces/event';

@Component({
  selector: 'situation-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css'],
  providers: [WebSocketAPI]
})
export class SituationNavbarComponent implements OnInit{
  events: Event[];
  sensors: Sensor[];
   constructor(private ws: WebSocketAPI){}
  ngOnInit(): void {
    this.ws.events.subscribe( value => {
      this.events = value;
    });
    this.ws.sensors.subscribe( value => {
      this.sensors = value;
    });
  }
}
