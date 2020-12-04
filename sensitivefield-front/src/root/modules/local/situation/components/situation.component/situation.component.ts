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
  // tslint:disable-next-line:component-selector
  selector: 'situation',
  templateUrl: 'situation.component.html',
  styleUrls: ['situation.component.css']
})
export class SituationComponent implements AfterViewInit, OnInit{
  public authority = '';
  public events: BehaviorSubject<AudioEvent> = new BehaviorSubject<AudioEvent>(null);
  public sensorNew: BehaviorSubject<AudioSensor> = new BehaviorSubject<AudioSensor>(null);
  public sensors: BehaviorSubject<AudioSensor[]> = new BehaviorSubject<AudioSensor[]>([]);
  public center: BehaviorSubject<LatLng> = new BehaviorSubject<LatLng>(new Leaflet.LatLng(55.49, 55.03));
  constructor(private tokenStorage: TokenStorageService, private router: ActivatedRoute, private route: Router, private as: ApiService) {}

  ngOnInit(): void {
    this.router.params.subscribe((params: Params) => {
      if (!params.lat || !params.lon) { return; }
      // tslint:disable-next-line:radix
      this.center.next(new Leaflet.LatLng(Number.parseInt(params.lat), Number.parseInt(params.lon)));
    });
  }

  ngAfterViewInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      this.as.tieAudioEvents().subscribe((response) => {
        this.events.next(JSON.parse((response as any).body));
      });
      this.as.getAudioSensors(1, 100).subscribe((response) => {
        this.sensors.next(response.audioSensors);
      });
      this.as.tieAudioSensors().subscribe((response) => {
        this.sensorNew.next(JSON.parse((response as any).body));
      });
    }
    else {
      this.route.navigate(['']);
    }
  }
}
