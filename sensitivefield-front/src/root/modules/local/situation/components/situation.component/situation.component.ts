import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { WebSocketService } from 'src/root/services/data-transfer/websocket/websocket.service';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
@Component({
  selector: 'situation',
  templateUrl: 'situation.component.html',
  styleUrls: ['situation.component.css']
})
export class SituationComponent implements AfterViewInit{
  public authority: string;
  public events: BehaviorSubject<AudioEvent> = new BehaviorSubject<AudioEvent>(null);
  public sensors: AudioSensor[];
  constructor(private tokenStorage: TokenStorageService, private router: Router, private ws: WebSocketService) {}

  ngAfterViewInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      this.ws._toDictionary('events',false,(response)=>{
        this.events.next(response);
      });
      this.ws._toDictionary('sensors',false,(response)=>{
        this.sensors = response;
      });
    }
    else {
      this.router.navigate(['']);
    }
  }
}
