import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { LatLng } from 'leaflet';
import { BehaviorSubject } from 'rxjs';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { Leaflet } from 'src/root/modules/public/map/services/leaflet.service';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';

@Component({
  selector: 'situation',
  templateUrl: 'situation.component.html',
  styleUrls: ['situation.component.css']
})
export class SituationComponent implements AfterViewInit, OnInit{
  public authority: string = '';
  public events: BehaviorSubject<AudioEvent[]> = new BehaviorSubject<AudioEvent[]>(null);
  public sensors: AudioSensor[] = [];
  public center: BehaviorSubject<LatLng> = new BehaviorSubject<LatLng>(new Leaflet.LatLng(0.0,0.0));

  constructor(private tokenStorage: TokenStorageService, private router: ActivatedRoute, private route: Router, private as: ApiService) {}

  ngOnInit(): void {
    this.router.params.subscribe((params: Params) => {
      if (!params['lat']||!params['lon']) return;
      this.center.next(new Leaflet.LatLng(Number.parseInt(params['lat']),Number.parseInt(params['lon'])))
    });
  }

  ngAfterViewInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      this.as.tieAudioEvents().subscribe((response)=>{
        this.events.next(response.values);
      });
      this.as.getAudioSensors().subscribe((response)=>{
        this.sensors = response.data;
      });
    }
    else {
      this.route.navigate(['']);
    }
  }
}
