import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import { TokenStorageService } from 'src/root/services/local-storage/token.storage.service';
@Component({
  selector: 'situation',
  templateUrl: 'situation.component.html',
  styleUrls: ['situation.component.css']
})
export class SituationComponent implements AfterViewInit{
  public authority: string;
  constructor(private tokenStorage: TokenStorageService, private router: Router) {}

  ngAfterViewInit(): void {
    if (this.tokenStorage.getToken()) {
      this.authority = this.tokenStorage.getAuthorities()[0];
      if (!this.authority) {
        if (this.authority === 'ROLE_USER') {
          // TODO: if user have USER_ROLE
        }
      }
    }
    else {
      this.router.navigate(['']);
    }
  }
}
