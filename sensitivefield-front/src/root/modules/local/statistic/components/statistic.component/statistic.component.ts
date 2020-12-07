import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import * as moment from 'moment';
import { BaseChartDirective, Color, Label } from 'ng2-charts';
import { MomentModule } from 'ngx-moment';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';
import { AudioSensor } from 'src/root/interfaces/audio-sensor.interface';
import { ApiService } from 'src/root/services/data-transfer/api/api.service';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
import { ChartOptions as CO } from '../../interfaces/options.interface';
@Component({
  selector: 'statistic',
  templateUrl: 'statistic.component.html',
  styleUrls: ['statistic.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class StatisticComponent implements OnInit {
  options: CO;
  openOptions: boolean = false;

  labels = [];
  full = [];
  doughnutFull = [];
  isLegends: boolean = false;

  timeSet: Date[] = [];

  doughnutChartType: string = 'doughnut';
  radarChartType: string = 'radar';
  lineChartType: string = 'line';
  barChartType: string = 'bar';

  cartOptipions = {
    responsive: true,
  };
  chartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(255,255,0,0.28)',
    },
  ];

  private audio_sensors: AudioSensor[] = [];
  private audio_events: AudioEvent[] = [];

  constructor(
    private tokenStorage: TokenStorageService,
    private as: ApiService
  ) {}

  setOptions(event) {
    this.options = event;

    this.as.getAudioSensors(1, 1000).subscribe(sensors => this.audio_sensors = sensors.audioSensors);
    this.as.getAudioEvents(
      1, 10000, this.options.opt3.end.toLocaleDateString() + ' ' + this.options.opt3.end.toLocaleTimeString(),
      this.options.opt3.start.toLocaleDateString() + ' ' + this.options.opt3.start.toLocaleTimeString()
    ).subscribe(events => {
      this.audio_events = (events as any).audioEventDTOS;
      this.options.opt1 === 'sensors'
        ? this.options.opt2 === 'kind'
        ? this.sensorsWithKind()
        : this.options.opt2 === 'type'
          ? this.sensorsWithType()
          : this.sensorsWithDanger()
        : this.options.opt2 === 'kind'
        ? this.eventsWithKind()
        : this.options.opt2 === 'type'
          ? this.eventsWithType()
          : this.eventsWithDanger();
    });
    this.openOptions = false;

    this.timeSet = this.splitTime();

  }
  splitTime(): Date[] {
    let start =
      (Date.UTC(
        this.options.opt3.start.getFullYear(),
        this.options.opt3.start.getMonth(),
        this.options.opt3.start.getDate()
      ) -
        Date.UTC(this.options.opt3.start.getFullYear(), 0, 0)) /
      24 /
      60 /
      60 /
      1000;
    let end =
      (Date.UTC(
        this.options.opt3.end.getFullYear(),
        this.options.opt3.end.getMonth(),
        this.options.opt3.end.getDate()
      ) -
        Date.UTC(this.options.opt3.end.getFullYear(), 0, 0)) /
      24 /
      60 /
      60 /
      1000;
    let empty = [];
    let interval = 60 * (((((end - start) * -1) * 24) + 23) / 8);
    let old = this.options.opt3.end;
    for (let index = 1; index < 8; index++) {
      let n = new Date();
      n = moment(old).add(interval * index, 'm').toDate();
      empty.push(n);
    }
    console.log(empty);
    return empty;
  }

  sensorsWithKind(): void {
    let labels = [];
    let empty = [];

    this.audio_events.map((event) => {
      if (!labels.includes(event.kindEvent)) {
        labels.push(event.kindEvent);
      }
    });

    this.audio_sensors.forEach((sensor) => {
      let data = [];
      labels.forEach((label) => {
        data.push(
          this.audio_events
            .filter((x) => x.sensor_id == sensor.id)
            .filter((x) => x.kindEvent == label).length
        );
      });
      empty.push({
        label: sensor.name ? sensor.name : sensor.id + '',
        data: data,
      });
    });

    this.full = empty;
    this.doughnutFull = this.full.map((value) => value.data);
    this.labels = labels;
    this.isLegends = true;
  }
  sensorsWithType(): void {
    let labels = [];
    let empty = [];

    this.audio_events.map((event) => {
      if (!labels.includes(event.typeSource1)) {
        labels.push(event.typeSource1);
      }
    });

    this.audio_sensors.forEach((sensor) => {
      let data = [];
      labels.forEach((label) => {
        data.push(
          this.audio_events
            .filter((x) => x.sensor_id == sensor.id)
            .filter((x) => x.typeSource1 == label).length
        );
      });
      empty.push({
        label: sensor.name ? sensor.name : sensor.id + '',
        data: data,
      });
    });

    this.full = empty;
    this.doughnutFull = this.full.map((value) => value.data);
    this.labels = labels;
    this.isLegends = true;
  }
  sensorsWithDanger(): void {
    let labels = ['First', 'Second', 'Third', 'Fourth'];
    let dangerKind = [
      ['nature', 'bike', 'people'],
      ['transport'],
      ['shoot', 'millitary transport'],
      ['plane', 'big boom'],
    ];
    let empty = [];

    this.audio_sensors.forEach((sensor) => {
      let data = [];
      labels.forEach((label, index) => {
        data.push(
          this.audio_events
            .filter((x) => x.sensor_id == sensor.id)
            .filter((x) => dangerKind[index].includes(x.kindEvent)).length
        );
      });
      empty.push({
        label: sensor.name ? sensor.name : sensor.id + '',
        data: data,
      });
    });

    this.full = empty;
    this.doughnutFull = this.full.map((value) => value.data);
    this.labels = labels;
    this.isLegends = true;
  }
  eventsWithKind(): void {
    let labels = [];
    let labelsKinds = [];
    let empty = [];


    let start = this.options.opt3.end.toLocaleDateString() + '|'  + this.options.opt3.end.toLocaleTimeString();
    let end = this.options.opt3.start.toLocaleDateString() + '|'  + this.options.opt3.start.toLocaleTimeString();
    this.timeSet.forEach((time, index) => {
      let label = time.toLocaleDateString() + '|' + time.toLocaleTimeString();
      labels.push(index!=0?
                  index!=7?
                  this.timeSet[index-1].toLocaleDateString() + '|' + this.timeSet[index-1].toLocaleTimeString() + ' - ' + label:
                  label + ' - ' + end: start + ' - ' + label);
    });
    this.audio_events.forEach((event) => {
      if (!labelsKinds.includes(event.kindEvent)) {
        labelsKinds.push(event.kindEvent);
      }
    });

    labels.forEach((timeLab, index) => {
      let evs: AudioEvent[] = [];
      this.as.getAudioEvents(
        1, 10000,
        index!=0?index!=7?
          this.timeSet[index-1].toLocaleDateString() + ' ' + this.timeSet[index-1].toLocaleTimeString():
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString():
          this.options.opt3.start.toLocaleDateString() + ' ' + this.options.opt3.start.toLocaleTimeString(),
        index!=0?index!=7?
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString():
          this.options.opt3.end.toLocaleDateString() + ' ' + this.options.opt3.end.toLocaleTimeString():
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString(),

      ).subscribe(events => {
          evs = (events as any).audioEventDTOS;
        let data = [];
        labelsKinds.forEach((label) => {
          //
          data.push(
            evs?.filter((x) => x.kindEvent === label).length
          );
        });
        empty.push({
          label: timeLab,
          data: data,
        });
      });
    });

    this.full = empty;
    this.doughnutFull = this.full.map((value) => value.data);
    this.labels = labelsKinds;
    this.isLegends = true;
  }
  eventsWithType(): void {
    let labels = [];
    let labelsKinds = [];
    let empty = [];


    let start = this.options.opt3.start.toLocaleDateString() + '|'  + this.options.opt3.start.toLocaleTimeString();
    let end = this.options.opt3.end.toLocaleDateString() + '|'  + this.options.opt3.end.toLocaleTimeString();
    this.timeSet.forEach((time, index) => {
      let label = time.toLocaleDateString() + '|' + time.toLocaleTimeString();
      labels.push(index!=0?
                  index!=7?
                  this.timeSet[index-1].toLocaleDateString() + '|' + this.timeSet[index-1].toLocaleTimeString() + ' - ' + label:
                  label + ' - ' + end: start + ' - ' + label);
    });

    this.audio_events.forEach((event) => {
      if (!labelsKinds.includes(event.typeSource1)) {
        labelsKinds.push(event.typeSource1);
      }
    });

    labels.forEach((timeLab, index) => {
      let evs: AudioEvent[] = [];
      this.as.getAudioEvents(
        1, 10000,
        index!=0?index!=7?
          this.timeSet[index-1].toLocaleDateString() + ' ' + this.timeSet[index-1].toLocaleTimeString():
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString():
          this.options.opt3.start.toLocaleDateString() + ' ' + this.options.opt3.start.toLocaleTimeString(),
        index!=0?index!=7?
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString():
          this.options.opt3.end.toLocaleDateString() + ' ' + this.options.opt3.end.toLocaleTimeString():
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString(),

      ).subscribe(events => {
        evs = (events as any).audioEventDTOS;
        let data = [];
        labelsKinds.forEach((label) => {
          //
          data.push(
            this.audio_events
              .filter((x) => x.typeSource1 === label).length
          );
        });
        empty.push({
          label: timeLab,
          data: data,
        });
      });
    });


    this.full = empty;
    this.doughnutFull = this.full.map((value) => value.data);
    this.labels = labelsKinds;
    this.isLegends = true;
  }
  eventsWithDanger(): void {
    let labels = [];
    let labelsKinds  = ['First', 'Second', 'Third', 'Fourth'];
    let dangerKind = [
      ['nature', 'bike', 'people'],
      ['transport'],
      ['shoot', 'millitary transport'],
      ['plane', 'big boom'],
    ];
    let empty = [];

    let start = this.options.opt3.start.toLocaleDateString() + '|'  + this.options.opt3.start.toLocaleTimeString();
    let end = this.options.opt3.end.toLocaleDateString() + '|'  + this.options.opt3.end.toLocaleTimeString();
    this.timeSet.forEach((time, index) => {
      let label = time.toLocaleDateString() + '|' + time.toLocaleTimeString();
      labels.push(index!=0?
                  index!=7?
                  this.timeSet[index-1].toLocaleDateString() + '|' + this.timeSet[index-1].toLocaleTimeString() + ' - ' + label:
                  label + ' - ' + end: start + ' - ' + label);
    });

    labels.forEach((timeLab, index) => {
      let evs: AudioEvent[] = [];
      this.as.getAudioEvents(
        1, 10000,
        index!=0?index!=7?
          this.timeSet[index-1].toLocaleDateString() + ' ' + this.timeSet[index-1].toLocaleTimeString():
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString():
          this.options.opt3.start.toLocaleDateString() + ' ' + this.options.opt3.start.toLocaleTimeString(),
        index!=0?index!=7?
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString():
          this.options.opt3.end.toLocaleDateString() + ' ' + this.options.opt3.end.toLocaleTimeString():
          this.timeSet[index].toLocaleDateString() + ' ' + this.timeSet[index].toLocaleTimeString(),

      ).subscribe(events => {
        evs = (events as any).audioEventDTOS;
        let data = [];
        labelsKinds.forEach((label) => {
          //
          data.push(
            this.audio_events
              .filter((x) => dangerKind[index].includes(x.kindEvent)).length
          );
        });
        empty.push({
          label: timeLab,
          data: data,
        });
      });
    });

    this.full = empty;
    this.doughnutFull = this.full.map((value) => value.data);
    this.labels = labelsKinds;
    this.isLegends = true;
  }

  //TODO: тимчасово
  // fillTheArrays() {
  //   this.audio_sensors = [
  //     {
  //       id: 23,
  //     } as AudioSensor,
  //     {
  //       id: 22,
  //     } as AudioSensor,
  //     {
  //       id: 21,
  //     } as AudioSensor,
  //     {
  //       id: 20,
  //       name: 'Tower-22',
  //     } as AudioSensor,
  //   ];
  //   this.audio_events = [
  //     {
  //       id: 111,
  //       sensor_id: 23,
  //       typeSource1: 'AK-47',
  //       kindEvent: 'shoot',
  //     } as AudioEvent,
  //     {
  //       id: 112,
  //       sensor_id: 23,
  //       typeSource1: 'AK-47',
  //       kindEvent: 'shoot',
  //     } as AudioEvent,
  //     {
  //       id: 113,
  //       sensor_id: 23,
  //       typeSource1: 'Tank',
  //       kindEvent: 'transport',
  //     } as AudioEvent,
  //     {
  //       id: 113,
  //       sensor_id: 22,
  //       typeSource1: 'Tank',
  //       kindEvent: 'transport',
  //     } as AudioEvent,
  //     {
  //       id: 114,
  //       sensor_id: 21,
  //       typeSource1: 'AK-47',
  //       kindEvent: 'shoot',
  //     } as AudioEvent,
  //     {
  //       id: 115,
  //       sensor_id: 20,
  //       typeSource1: 'Bike',
  //       kindEvent: 'transport',
  //     } as AudioEvent,
  //     {
  //       id: 116,
  //       sensor_id: 21,
  //       typeSource1: 'BNP',
  //       kindEvent: 'shoot',
  //     } as AudioEvent,
  //     {
  //       id: 117,
  //       sensor_id: 22,
  //       typeSource1: 'Car',
  //       kindEvent: 'transport',
  //     } as AudioEvent,
  //     {
  //       id: 118,
  //       sensor_id: 21,
  //       typeSource1: 'AK-47',
  //       kindEvent: 'shoot',
  //     } as AudioEvent,
  //     {
  //       id: 119,
  //       sensor_id: 21,
  //       typeSource1: 'Car',
  //       kindEvent: 'transport',
  //     } as AudioEvent,
  //     {
  //       id: 120,
  //       sensor_id: 23,
  //       typeSource1: 'BNP',
  //       kindEvent: 'shoot',
  //     } as AudioEvent,
  //     {
  //       id: 121,
  //       sensor_id: 21,
  //       typeSource1: 'Deer',
  //       kindEvent: 'nature_sound',
  //     } as AudioEvent,
  //   ];
  // }
  public barChartOptions = {
    scaleShowVerticalLines: true,
    responsive: true,
  };

  ngOnInit(): void {
  }
}
