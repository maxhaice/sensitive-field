import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { AudioEvent } from 'src/root/interfaces/audio-event.interface';

@Component({
  selector: 'time-line',
  templateUrl: 'time-line.component.html',
  styleUrls: ['time-line.component.css'],
})
export class NavbarTimeLineComponent implements OnChanges{
  @Input() input: AudioEvent;
  protected _input = {
    size: 21,
    start: 0,
    end: 1,
    data: []
  };
  protected _bigData: AudioEvent[] = [];
  protected _break: boolean = false;
  constructor(){}
  ngOnChanges(changes: SimpleChanges): void {
    if( !this._break &&  this.input!=this._bigData[this._bigData.length-1]){
      this._bigData.push(this.input);
      if (this._bigData.length<=21){
        this._input.end++;
        this._input.data.push(this.input);
      }
    }
  }
  toNext(): void{
    if(!this.hasNext()) return;
    this._input.data = this._bigData.slice(this._input.start+=1,this._input.end+=1);
  }
  hasNext(): boolean{
    return this._input.end!=this._bigData.length-1&&this._bigData.length>21;
  }
  toPrevious(): void{
    if(!this.hasPrevious()) return;
    this._input.data = this._bigData.slice(this._input.start-=1,this._input.end-=1);
  }
  hasPrevious(): boolean{
    return this._input.start!=0&&this._bigData.length>0;
  }
}
