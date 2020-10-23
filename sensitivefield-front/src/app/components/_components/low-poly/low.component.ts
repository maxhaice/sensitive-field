import {Component, OnInit} from '@angular/core';
import { TokenStorageService } from 'src/app/services/auth/token.storage.service';
@Component({
  selector: 'low-poly',
  templateUrl: 'low.component.html',
  styleUrls: ['low.component.css']
})

export class LowComponent implements OnInit{
  authority: string = "";
 
  constructor(private tokenStorage: TokenStorageService) { }
 
  ngOnInit() {
  }
}
