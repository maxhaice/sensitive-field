import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { LatLng } from 'leaflet';
import { BehaviorSubject } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { Leaflet } from 'src/root/modules/public/map/services/leaflet.service';
import { NEW_EVENTS, SENSORS } from 'src/root/services/data-transfer/api/subscribers/subscribe-const/const.subscribe';
import { WebSocketService } from 'src/root/services/data-transfer/websocket/websocket.service';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
@Component({
  selector: 'situation',
  templateUrl: 'situation.component.html',
  styleUrls: ['situation.component.css']
})
export class SituationComponent implements AfterViewInit, OnInit{
  public authority: string;
  public events: BehaviorSubject<AudioEvent[]> = new BehaviorSubject<AudioEvent[]>(null);
  public sensors: AudioSensor[];
  public center: BehaviorSubject<LatLng> = new BehaviorSubject<LatLng>(new Leaflet.LatLng(0.0,0.0));
  constructor(private tokenStorage: TokenStorageService, private router: ActivatedRoute, private route: Router, private ws: WebSocketService) {}
  ngOnInit(): void {
    this.router.params.subscribe((params: Params) => {
      if (!params['lat']||!params['lon']) return;
      this.center.next(new Leaflet.LatLng(Number.parseInt(params['lat']),Number.parseInt(params['lon'])))
    });
  }

  ngAfterViewInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      this.ws._toDictionary(NEW_EVENTS,false,(response)=>{
        this.events.next(response);
      });
      this.ws._toDictionary(SENSORS,false,(response)=>{
        this.sensors = response;
      });
    }
    else {
      this.route.navigate(['']);
    }
  }
}
