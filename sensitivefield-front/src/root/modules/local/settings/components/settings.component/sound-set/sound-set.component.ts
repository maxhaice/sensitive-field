import {Component, ElementRef, HostBinding, OnInit, Sanitizer} from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
import { FireCommunication } from 'src/app/services/fire/firecomunication.service';

@Component({
  selector: 'sound-set',
  templateUrl: 'sound-set.component.html',
  styleUrls: ['sound-set.component.css'],
  providers: [FireCommunication]
})

export class SoundSetComponent implements OnInit{
  isSoundAll: boolean;
  dangerSounds: boolean;
  shootSounds: boolean;
  carSounds: boolean;
  anotherSounds: boolean;
  constructor( ) { }
  ngOnInit(): void {
    this.isSoundAll = FireCommunication.isSoundAll();
    if (this.isSoundAll){
      this.dangerSounds = this.isSoundAll;
      this.shootSounds = this.isSoundAll;
      this.carSounds = this.isSoundAll;
      this.anotherSounds = this.isSoundAll;
    }
  }

  updateAllComplete() {
    this.isSoundAll = this.dangerSounds && this.carSounds && this.anotherSounds && this.shootSounds;
  }

  someComplete(): boolean {
    return (this.dangerSounds || this.carSounds || this.anotherSounds || this.shootSounds) && !this.isSoundAll;
  }
  setSoundAll(all: boolean): void {
    this.isSoundAll = all;
    this.dangerSounds = this.isSoundAll;
    this.shootSounds = this.isSoundAll;
    this.carSounds = this.isSoundAll;
    this.anotherSounds = this.isSoundAll;
    FireCommunication.isSoundAll(all);
  }
}
