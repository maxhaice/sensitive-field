import {Component, ElementRef, HostBinding, OnInit, Sanitizer} from '@angular/core';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
import { FireCommunication } from 'src/app/services/fire/firecomunication.service';

@Component({
  selector: 'design-set',
  templateUrl: 'design-set.component.html',
  styleUrls: ['design-set.component.css'],
  providers: [FireCommunication]
})

export class DesignSetComponent implements OnInit{
  isHideNavbar: boolean;
  background: string;
  s_background: string;
  textColor: string;
  s_textColor: string;
  constructor( ) { }
  ngOnInit(): void {
    this.isHideNavbar = FireCommunication.isHide().getValue();
    this.s_textColor = document.documentElement.style.getPropertyValue('--primary-color');
    this.textColor = document.documentElement.style.getPropertyValue('--text-color');
    this.s_background = document.documentElement.style.getPropertyValue('--secondary-color');
    this.background = document.documentElement.style.getPropertyValue('--background-color');
  }

  setBackground(): void {
    FireCommunication.styleBackground(this.background);
  }
  setSecondaryBackground(): void {
    FireCommunication.styleSecondaryBackground(this.s_background);
  }
  setTextColor(): void {
    FireCommunication.styleTextColor(this.textColor);
  }
  setSecondaryTextColor(): void {
    FireCommunication.styleSecondaryTextColor(this.s_textColor);
  }
  setHideNavbar(): void{
    FireCommunication.isHide(this.isHideNavbar);
  }
}
