import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
@Component({
  selector: 'settings',
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SettingsComponent implements OnInit{
  public authority: string;
  constructor(private tokenStorage: TokenStorageService, private router: Router) {}
  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      if (!this.authority) {
        if (this.authority === 'ROLE_USER') {
          this.router.navigate(['']);
        }
      }
    }
    else {
      this.router.navigate(['']);
    }
  }
}
