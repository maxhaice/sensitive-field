import {Component, ElementRef, HostBinding, OnInit, Sanitizer} from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
import { FireCommunication } from 'src/app/services/fire/firecomunication.service';

@Component({
  selector: 'filter-stat',
  templateUrl: 'filter-stat.component.html',
  styleUrls: ['filter-stat.component.css'],
  providers: [FireCommunication]
})

export class FilterStatComponent implements OnInit{
  constructor( ) { }
  ngOnInit(): void {
  }
}
