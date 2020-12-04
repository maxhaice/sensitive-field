import { Component, Input } from '@angular/core';
@Component({
  // tslint:disable-next-line:component-selector
  selector: 'default',
  templateUrl: 'default.component.html',
  styleUrls: ['default.component.css']
})

export class DefaultComponent{
  @Input() error = '404';
  constructor() { }
}
