import { Component, Input } from '@angular/core';
@Component({
  selector: 'default',
  templateUrl: 'default.component.html',
  styleUrls: ['default.component.css']
})

export class DefaultComponent{
  @Input() error?: string = '404';
  constructor() { }
}
