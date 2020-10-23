import {Injectable} from '@angular/core';
import { FireCommunication } from './firecomunication.service';

@Injectable()
export class FireSound {
  constructor() { }
  static audio = new Audio('assets/alert.mp3');
  static execute(): void{
    if (!FireCommunication.isSoundAll()) { return; }
    if (this.audio.paused && this.audio.currentTime > 0 && !this.audio.ended) {
      this.audio.play();
  } else {
      this.audio.pause();
      this.audio.play();
  }
  }
}